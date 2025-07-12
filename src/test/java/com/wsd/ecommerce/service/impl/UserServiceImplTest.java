package com.wsd.ecommerce.service.impl;

import com.wsd.ecommerce.constant.UserType;
import com.wsd.ecommerce.dto.PaginationArgs;
import com.wsd.ecommerce.dto.request.LoginRequest;
import com.wsd.ecommerce.dto.request.RegistrationRequest;
import com.wsd.ecommerce.dto.response.LoginResponse;
import com.wsd.ecommerce.entity.User;
import com.wsd.ecommerce.exception.ApplicationException;
import com.wsd.ecommerce.exception.BadCredentialException;
import com.wsd.ecommerce.repository.UserRepository;
import com.wsd.ecommerce.security.jwt.JwtTokenProvider;
import com.wsd.ecommerce.service.UserService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider tokenProvider;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldRegisterCustomerUserSuccessfully() {
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .email("test@gmail.com").name("abc").password("abcd@1234").build();

        User user = getSampleUser();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        assertThatCode(() -> userService.register(registrationRequest)).doesNotThrowAnyException();
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldNotRegisterIfEmailAlreadyExists() {
        User user = getSampleUser();
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .email("test@gmail.com").name("abc").password("abcd@1234").build();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> userService.register(registrationRequest)).isInstanceOf(ApplicationException.class);
    }

    private User getSampleUser() {
        return User.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .password("password123") // in actual usage this should be hashed
                .userType(UserType.CUSTOMER)
                .build();
    }

    @Test
    void shouldLoginSuccessfullyWithValidCredentials() {
        User user = getSampleUser();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any())).thenReturn(mock(Authentication.class));
        when(tokenProvider.generateToken(any())).thenReturn("fake-jwt-token");

        LoginRequest request = LoginRequest.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();

        LoginResponse response = userService.login(request);

        assertThat(response).isNotNull();
        assertThat(response.accessToken()).isEqualTo("fake-jwt-token");
    }

    @Test
    void shouldFailLoginWhenUserNotFound() {
        when(userRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());

        LoginRequest request = LoginRequest.builder()
                .email("missing@example.com")
                .password("any")
                .build();

        assertThatThrownBy(() -> userService.login(request))
                .isInstanceOf(ApplicationException.class);
    }

    @Test
    void shouldFailLoginWhenPasswordIncorrect() {
        User user = getSampleUser();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any())).thenThrow(BadCredentialException.class);

        LoginRequest request = LoginRequest.builder()
                .email("whereismypassword@example.com")
                .password("iforgotmypassword, please help")
                .build();

        assertThatThrownBy(() -> userService.login(request))
                .isInstanceOf(ApplicationException.class);
    }

    @Test
    void shouldGetAllCustomersPaginated() {
        User user = getSampleUser();
        Page<User> page = new PageImpl<>(List.of(user));
        when(userRepository.findAllByUserType(eq(UserType.CUSTOMER), any(PageRequest.class))).thenReturn(page);

        Page<User> result = userService.allCustomers(new PaginationArgs(0, 10));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getUserType()).isEqualTo(UserType.CUSTOMER);
    }
}