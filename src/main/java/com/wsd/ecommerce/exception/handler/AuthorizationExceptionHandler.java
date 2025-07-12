package com.wsd.ecommerce.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wsd.ecommerce.dto.response.DefaultErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorizationExceptionHandler implements AuthenticationEntryPoint, Serializable {
    private static final String AUTH_ERROR_CODE = "AUTHENTICATION_FAILED";

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder headers = new StringBuilder();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.append(headerName).append(": ").append(headerValue).append("\n");
        }

//        log.info("Request Headers:\n{}", headers);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        DefaultErrorResponse errorCodeEntity = DefaultErrorResponse
                .builder()
                .message("You don't have permission to access this section")
                .errorCode(AUTH_ERROR_CODE)
                .build();

        String responseMsg = objectMapper.writeValueAsString(errorCodeEntity);
        response.getWriter().write(responseMsg);
        response.setStatus(403);
    }
}
