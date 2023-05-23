package com.example.cargo_transportation.security;

import com.example.cargo_transportation.entity.SessionJWT;
import com.example.cargo_transportation.entity.User;
import com.example.cargo_transportation.modal.payload.response.JWTToken;
import com.example.cargo_transportation.service.SessionJWTService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
public class JWTTokenProvider {
    @Value("${security-setting.access-expiration-time}")
    private String accessExpirationTime;
    @Value("${security-setting.refresh-expiration-time}")
    private String refreshExpirationTime;
    @Value("${security-setting.token-prefix}")
    private String tokenPrefix;
    @Value("${security-setting.header-string}")
    private String headerString;
    @Value("${security-setting.allowed-clock-skew-seconds}")
    private Long allowedClockSkewSeconds;

    private final SessionJWTService sessionJWTService;
    private final SigningKeyResolver signingKeyResolver;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JWTTokenProvider(SessionJWTService sessionJWTService, UserDetailsService userDetailsService) {
        this.sessionJWTService = sessionJWTService;
        this.userDetailsService = userDetailsService;
        this.signingKeyResolver = createSigningKeyResolver();
    }

    public UserDetails getUserDetailsByRequest(HttpServletRequest request, TypeToken typeToken) {
        String username = getUsernameFromToken(getJWTFromRequest(request), typeToken);
        return StringUtils.hasText(username) ? userDetailsService.loadUserByUsername(username) : null;
    }

    public JWTToken getTokens(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date accessExpiryDate = new Date(now.getTime() + Long.parseLong(accessExpirationTime));
        Date refreshExpiryDate = new Date(now.getTime() + Long.parseLong(refreshExpirationTime));
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String secretKeyString = Encoders.BASE64.encode(secretKey.getEncoded());

        String accessToken = generateToken(user, TypeToken.ACCESS_TOKEN, secretKey, accessExpiryDate, now);
        String refreshToken = generateToken(user, TypeToken.REFRESH_TOKEN, secretKey, refreshExpiryDate, now);

        sessionJWTService.saveOrUpdateSession(user, secretKeyString);

        return new JWTToken(
                200,
                tokenPrefix + " ",
                accessToken,
                accessExpiryDate,
                refreshToken,
                refreshExpiryDate
        );
    }

    public void deleteSession(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        sessionJWTService.deleteSession(user);
    }

    private String generateToken(User user, TypeToken typeToken, SecretKey secretKey, Date expiryDate, Date now) {
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("typeToken", typeToken.toString());
        claimsMap.put("userId", user.getId());
        claimsMap.put("username", user.getUsername());
        claimsMap.put("firstname", user.getFirstname());
        claimsMap.put("lastname", user.getLastname());

        return Jwts.builder()
                .addClaims(claimsMap)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    private String getUsernameFromToken(String token, TypeToken typeToken) {
        try {
            if (StringUtils.hasText(token)) {
                Claims claims = Jwts.parserBuilder()
                        .setAllowedClockSkewSeconds(allowedClockSkewSeconds)
                        .setSigningKeyResolver(signingKeyResolver)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
                String typeTokenString = (String) claims.get("typeToken");
                if (TypeToken.valueOf(typeTokenString).equals(typeToken))
                    return (String) claims.get("username");
            }
        } catch (JwtException | IllegalArgumentException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(headerString);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(tokenPrefix + " ")) {
            return bearerToken.split(" ")[1];
        }
        return null;
    }

    private SigningKeyResolver createSigningKeyResolver() {
        return new SigningKeyResolverAdapter() {
            @Override
            public Key resolveSigningKey(JwsHeader header, Claims claims) {
                String username = (String) claims.get("username");
                SessionJWT sessionJWT = sessionJWTService.getByUsername(username);
                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(sessionJWT.getSecretKey()));
            }
        };
    }
}
