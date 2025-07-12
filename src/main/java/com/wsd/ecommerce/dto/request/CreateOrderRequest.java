package com.wsd.ecommerce.dto.request;


import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record CreateOrderRequest(@NotEmpty(message = "atleast one item is needed for order")
                                 Set<OrderItemRequest> orderItems) {
}