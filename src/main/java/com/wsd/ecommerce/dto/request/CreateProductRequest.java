package com.wsd.ecommerce.dto.request;

import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record CreateProductRequest(@Length(min = 2, max = 50, message = "name is too long or short") String name,
                                   @Length(min = 1, max = 255, message = "description is too long or short")
                                   String description,
                                   @Positive BigDecimal price) {
}
