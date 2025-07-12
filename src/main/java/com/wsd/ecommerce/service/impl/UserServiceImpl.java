package com.wsd.ecommerce.service.impl;

import com.wsd.ecommerce.dto.PaginationArgs;
import com.wsd.ecommerce.dto.request.LoginRequest;
import com.wsd.ecommerce.dto.request.RegistrationRequest;
import com.wsd.ecommerce.dto.response.LoginResponse;
import com.wsd.ecommerce.entity.User;
import com.wsd.ecommerce.service.UserService;
import org.springframework.data.domain.Page;

public class UserServiceImpl implements UserService {
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public Page<User> allCustomers(PaginationArgs paginationArgs) {
        return null;
    }

    @Override
    public void register(RegistrationRequest registrationRequest) {

    }
}
