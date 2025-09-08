package com.spring_redis_service.advice.exception;

import com.spring_redis_service.advice.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpringRedisServiceException extends RuntimeException {

    private final String code;
    private final String message;
    private final Integer httpStatusCode;

    public SpringRedisServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.httpStatusCode = errorCode.getHttpStatusCode();
    }
}
