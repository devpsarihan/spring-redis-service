package com.spring_redis_service.configuration.redis;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisConstant {

    public static final String PRODUCT_CACHE = "product";
    public static final Long PRODUCT_TTL = 60000L;
}
