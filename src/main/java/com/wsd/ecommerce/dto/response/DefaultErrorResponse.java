package com.wsd.ecommerce.dto.response;

import lombok.Builder;

@Builder
public record DefaultErrorResponse(String message, String errorCode) {
}
