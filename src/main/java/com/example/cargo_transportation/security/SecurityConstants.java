package com.example.cargo_transportation.security;

public class SecurityConstants {

    public static final String[] SIGN_UP_URLS = {"/api/auth/**", "/swagger-ui/**", "/api-docs/**"};
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
    public static final long EXPIRATION_TIME = 600_000;//10мин
    public static final long REFRESH_EXPIRATION_TIME = 3_600_000;//час

}
