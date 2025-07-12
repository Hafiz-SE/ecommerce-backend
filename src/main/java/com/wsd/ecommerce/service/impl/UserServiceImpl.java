package com.wsd.ecommerce.service.impl;

import com.wsd.ecommerce.constant.SecurityConstant;
import com.wsd.ecommerce.constant.UserType;
import com.wsd.ecommerce.dto.PaginationArgs;
import com.wsd.ecommerce.dto.request.LoginRequest;
import com.wsd.ecommerce.dto.request.RegistrationRequest;
import com.wsd.ecommerce.dto.response.LoginResponse;
import com.wsd.ecommerce.entity.User;
import com.wsd.ecommerce.exception.ApplicationException;
import com.wsd.ecommerce.repository.UserRepository;
import com.wsd.ecommerce.security.jwt.JwtTokenProvider;
import com.wsd.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email()).orElse(null);
        if (Objects.isNull(user) || !passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new ApplicationException("Username/Password incorrect");
        }

        log.info("Login :: User Found. User : {}", user);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                loginRequest.email(), loginRequest.password()
        );

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(authToken);
        } catch (Exception ex) {
            throw new ApplicationException("Invalid credentials: " + ex.getMessage());
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        if (Objects.isNull(jwt)) {
            log.error("Login :: Token generation failed");
            throw new ApplicationException("Something went wrong.");
        }

        return LoginResponse.builder()
                .accessToken(jwt)
                .tokenExpiryTimeInSecond(3600)
                .tokenType(SecurityConstant.TOKEN_PREFIX)
                .build();
    }

    @Override
    public Page<User> allCustomers(PaginationArgs paginationArgs) {
        return userRepository.findAllByUserType(UserType.CUSTOMER,
                PageRequest.of(paginationArgs.getPageNo(), paginationArgs.getPageSize()));
    }

    @Override
    public void register(RegistrationRequest registrationRequest) {
        userRepository.findByEmail(registrationRequest.email()).ifPresent(u -> {
            throw new ApplicationException("Email already registered");
        });

        User user = User.builder()
                .name(registrationRequest.name())
                .email(registrationRequest.email())
                .password(passwordEncoder.encode(registrationRequest.password()))
                .userType(UserType.CUSTOMER)
                .build();

        userRepository.save(user);
    }
}
