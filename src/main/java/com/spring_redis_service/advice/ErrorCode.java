package com.spring_redis_service.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    CATEGORY_NOT_FOUND_ERROR("001", "Category is not found.", 404),
    PRODUCT_NOT_FOUND_ERROR("002", "Product is not found.", 404);

    private final String code;
    private final String message;
    private final Integer httpStatusCode;
}
