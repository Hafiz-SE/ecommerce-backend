package com.wsd.ecommerce.controller;

import com.wsd.ecommerce.dto.request.LoginRequest;
import com.wsd.ecommerce.dto.request.RegistrationRequest;
import com.wsd.ecommerce.dto.response.LoginResponse;
import com.wsd.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerCustomer(@Valid @RequestBody RegistrationRequest regRequest) {
        userService.register(regRequest);
    }
}
