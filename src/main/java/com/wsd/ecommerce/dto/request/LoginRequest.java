package com.wsd.ecommerce.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record LoginRequest(@NotBlank(message = "cannot be blank")
                           @Email(message = "mail should be valid")
                           String email,
                           @NotBlank(message = "password cannot be empty")
                           @Length(min = 8, message = "invalid username/password")
                           String password) {
}
