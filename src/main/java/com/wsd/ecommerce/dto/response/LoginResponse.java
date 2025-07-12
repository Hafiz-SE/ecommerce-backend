package com.wsd.ecommerce.dto.response;

import lombok.Builder;

@Builder
public record LoginResponse(String tokenType, String accessToken, Integer tokenExpiryTimeInSecond) {
}
