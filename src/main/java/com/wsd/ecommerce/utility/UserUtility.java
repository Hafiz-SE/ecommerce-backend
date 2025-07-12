package com.wsd.ecommerce.utility;

import com.wsd.ecommerce.entity.User;
import com.wsd.ecommerce.security.custom.user.CustomUserDetails;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class UserUtility {
    public static User getCurrentUser() {
        CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return User.builder()
                .id(principal.getId())
                .email(principal.getUsername())
                .userType(principal.getUserType())
                .build();

    }
}
