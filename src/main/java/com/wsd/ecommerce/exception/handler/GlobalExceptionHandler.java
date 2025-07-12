package com.wsd.ecommerce.exception.handler;

import com.wsd.ecommerce.dto.response.DefaultErrorResponse;
import com.wsd.ecommerce.exception.ApplicationException;
import com.wsd.ecommerce.exception.BadCredentialException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<DefaultErrorResponse> handleUserAlreadyExists(ApplicationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(DefaultErrorResponse.builder().message(ex.getMessage()).build());
    }

    @ExceptionHandler(BadCredentialException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<DefaultErrorResponse> handleBadCredentialException(BadCredentialException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(DefaultErrorResponse.builder().message(ex.getMessage()).build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        DefaultErrorResponse errorResponse = DefaultErrorResponse.builder()
                .message(errorMessage)
                .errorCode("VALIDATION_ERROR")
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
