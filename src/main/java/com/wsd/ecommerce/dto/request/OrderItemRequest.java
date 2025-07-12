package com.wsd.ecommerce.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequest(@NotNull(message = "Product id cannot be null")
                               Long productId,
                               @NotNull(message = "quantity cannot be null")
                               @Min(value = 1, message = "quantity has to be greater or equal to 1")
                               Integer quantity) {
}
