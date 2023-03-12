package com.example.cargo_transportation.impl;

import com.example.cargo_transportation.payload.request.LoginRequest;
import com.example.cargo_transportation.payload.request.RefreshToken;
import com.example.cargo_transportation.payload.response.JWTToken;
import com.example.cargo_transportation.security.JWTTokenProvider;
import com.example.cargo_transportation.service.AuthenticatedService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import static com.example.cargo_transportation.security.TypeToken.REFRESH_TOKEN;

@Service
public class AuthenticatedServiceImpl implements AuthenticatedService {
    private final JWTTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticatedServiceImpl(JWTTokenProvider jwtTokenProvider,
                                    AuthenticationManager authenticationManager){
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    public JWTToken authenticateUser(@RequestBody @Valid LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.getTokens(authentication);
    }

    public JWTToken refreshUser(@Valid @RequestBody RefreshToken refreshToken) {
        UserDetails userDetails = jwtTokenProvider.getUserDetailsByToken(refreshToken.getToken(), REFRESH_TOKEN);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.getTokens(authentication);
    }
}

