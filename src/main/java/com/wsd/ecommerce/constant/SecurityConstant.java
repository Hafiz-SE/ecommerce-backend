package com.wsd.ecommerce.constant;

import java.util.concurrent.TimeUnit;

public class SecurityConstant {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = TimeUnit.DAYS.toMillis(1);
    public static final String SECRET = "2034f6e32958647fdff75d265b455ebf2034f6e32958647fdff75d265b455ebf2034f6e32958647fdff75d265b455ebf";

    public static final String[] JWTDisabledAntMatchers = {
            "/auth/**",
            "/swagger-ui/**",
            "/v3/api-docs*/**",
            "/actuator/**",
            "/product/all"
    };
}
