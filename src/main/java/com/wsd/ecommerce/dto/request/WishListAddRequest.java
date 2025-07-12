package com.wsd.ecommerce.dto.request;

import jakarta.validation.constraints.NotNull;

public record WishListAddRequest(@NotNull Long productId) {
}
