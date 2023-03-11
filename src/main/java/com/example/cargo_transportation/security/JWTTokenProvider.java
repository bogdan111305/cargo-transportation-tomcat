package com.example.cargo_transportation.security;

import com.example.cargo_transportation.entity.RefreshToken;
import com.example.cargo_transportation.entity.User;
import com.example.cargo_transportation.payload.response.JWTToken;
import com.example.cargo_transportation.service.RefreshTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.cargo_transportation.security.TypeToken.ACCESS_TOKEN;
import static com.example.cargo_transportation.security.TypeToken.REFRESH_TOKEN;

@Component
@Log4j2
public class JWTTokenProvider {

    private static SecretKey secretKey;
    private RefreshTokenService refreshTokenService;
    private UserDetailsService userDetailsService;

    @Autowired
    public JWTTokenProvider(RefreshTokenService refreshTokenService, UserDetailsService userDetailsService) {
        this.refreshTokenService = refreshTokenService;
        this.userDetailsService = userDetailsService;
        generateSecretKey();
    }

    public UserDetails getUserByToken(String token, TypeToken typeToken) {
        if (typeToken.equals(REFRESH_TOKEN)) {
            RefreshToken refreshToken = refreshTokenService.findByToken(token);
            refreshTokenService.deleteToken(refreshToken);
        }
        if (validateToken(token)) {
            String username = getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return userDetails;
        }
        return null;
    }

    public JWTToken getTokens(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Date now = new Date(System.currentTimeMillis());
        Date accessExpiryDate = new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME);
        Date expiryRefreshDate = new Date(now.getTime() + SecurityConstants.REFRESH_EXPIRATION_TIME);

        String accessToken = generateToken(user, accessExpiryDate, now);
        String refreshToken = generateToken(user, expiryRefreshDate, now);

        refreshTokenService.saveRefreshToken(refreshToken, (Principal) authentication.getPrincipal());

        return new JWTToken(true, SecurityConstants.TOKEN_PREFIX, accessToken, refreshToken);
    }

    private String generateToken(User user, Date expiryDate, Date now) {
        String userId = Long.toString((user.getId()));

        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("username", user.getUsername());
        claimsMap.put("firstname", user.getFirstname());
        claimsMap.put("lastname", user.getLastname());

        return Jwts.builder()
                .setSubject(userId)
                .addClaims(claimsMap)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            if (StringUtils.hasText(token)) {
                Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .setAllowedClockSkewSeconds(180)
                        .build()
                        .parseClaimsJws(token);
                return true;
            }
        } catch (JwtException | IllegalArgumentException e) {
            log.error(e.getMessage());
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return (String) claims.get("username");
    }

    private void generateSecretKey() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
}
