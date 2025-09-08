package com.spring_redis_service.configuration.redis;

import static com.spring_redis_service.configuration.redis.RedisConstant.PRODUCT_CACHE;
import static com.spring_redis_service.configuration.redis.RedisConstant.PRODUCT_TTL;

import io.lettuce.core.RedisURI;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Setter
@Configuration
@RequiredArgsConstructor
public class RedisConfiguration {

    private final RedisProperties redisProperties;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = getRedisStandaloneConfiguration(redisProperties);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisCacheManager redisCacheManager() {
        return RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(jedisConnectionFactory())
            .withInitialCacheConfigurations(redisCacheConfigurations())
            .build();
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }

    private RedisStandaloneConfiguration getRedisStandaloneConfiguration(RedisProperties redisProperties) {
        RedisURI redisURI = RedisURI.create(redisProperties.getUrl());
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisURI.getHost());
        configuration.setPort(redisURI.getPort());
        configuration.setDatabase(redisProperties.getDatabase());
        configuration.setPassword(RedisPassword.of(redisProperties.getPassword()));
        return configuration;
    }

    private Map<String, RedisCacheConfiguration> redisCacheConfigurations() {
        Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>();
        RedisCacheConfiguration productRedisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMillis(PRODUCT_TTL))
            .disableCachingNullValues();
        configurationMap.put(PRODUCT_CACHE, productRedisCacheConfiguration);
        return configurationMap;
    }
}
