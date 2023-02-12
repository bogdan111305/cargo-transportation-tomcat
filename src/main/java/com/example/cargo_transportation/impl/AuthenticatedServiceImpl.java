package com.example.cargo_transportation.impl;

import com.example.cargo_transportation.payload.request.LoginRequest;
import com.example.cargo_transportation.payload.response.JWTTokenSuccessResponse;
import com.example.cargo_transportation.security.JWTTokenProvider;
import com.example.cargo_transportation.security.SecurityConstants;
import com.example.cargo_transportation.service.AuthenticatedService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Log4j2
public class AuthenticatedServiceImpl implements AuthenticatedService {

    private final JWTTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticatedServiceImpl(JWTTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager){
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    public JWTTokenSuccessResponse authenticateUser(@RequestBody @Valid LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);

        log.info(token);

        return new JWTTokenSuccessResponse(true, token);
    }
}

