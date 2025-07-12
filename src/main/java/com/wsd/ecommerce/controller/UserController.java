package com.wsd.ecommerce.controller;

import com.wsd.ecommerce.dto.PaginationArgs;
import com.wsd.ecommerce.entity.User;
import com.wsd.ecommerce.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public Page<User> getPaginatedCustomers(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        return userService.allCustomers(PaginationArgs.builder().pageNo(pageNo).pageSize(pageSize).build());
    }
}
