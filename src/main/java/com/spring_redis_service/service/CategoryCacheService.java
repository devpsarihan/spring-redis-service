package com.spring_redis_service.service;

import com.spring_redis_service.model.dto.CategoryDto;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryCacheService {

    private static final String CATEGORY = "category";
    private static final String DELIMITER = "::";

    private final RedisTemplate<String, CategoryDto> categoryRedisTemplate;

    public void cacheCategory(final Long categoryId, final CategoryDto categoryDto) {
        String key = getKey(categoryId);
        categoryRedisTemplate.opsForValue().set(key, categoryDto, Duration.ofMinutes(1));
    }

    public CategoryDto getCategoryById(final Long categoryId) {
        String key = getKey(categoryId);
        return categoryRedisTemplate.opsForValue().get(key);
    }

    public void evictCategoryCache(final Long categoryId) {
        String key = getKey(categoryId);
        categoryRedisTemplate.delete(key);
    }

    private String getKey(final Long categoryId) {
        return CATEGORY.concat(DELIMITER).concat(categoryId.toString());
    }
}
