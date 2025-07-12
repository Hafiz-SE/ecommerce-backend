package com.wsd.ecommerce.service;

import com.wsd.ecommerce.dto.PaginationArgs;
import com.wsd.ecommerce.dto.request.LoginRequest;
import com.wsd.ecommerce.dto.request.RegistrationRequest;
import com.wsd.ecommerce.dto.response.LoginResponse;
import com.wsd.ecommerce.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {
    LoginResponse login(LoginRequest loginRequest);

    Page<User> allCustomers(PaginationArgs paginationArgs);

    void register(RegistrationRequest registrationRequest);
}
