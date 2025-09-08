package com.spring_redis_service.advice;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpringRedisErrorResponse implements Serializable {

    private String code;
    private String message;
}
