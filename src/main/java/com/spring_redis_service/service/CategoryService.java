package com.spring_redis_service.service;

import static com.spring_redis_service.advice.ErrorCode.CATEGORY_NOT_FOUND_ERROR;

import com.spring_redis_service.advice.exception.SpringRedisServiceException;
import com.spring_redis_service.model.dto.CategoryDto;
import com.spring_redis_service.model.entity.Category;
import com.spring_redis_service.model.request.CreateCategoryRequest;
import com.spring_redis_service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryCacheService categoryCacheService;
    private final CategoryRepository categoryRepository;

    public Long createCategory(final CreateCategoryRequest request) {
        return categoryRepository.save(
            Category.builder()
                .name(request.getName())
                .build()).getId();
    }

    public CategoryDto getCategoryById(final Long categoryId) {
        CategoryDto cachedCategoryDto = categoryCacheService.getCategoryById(categoryId);
        if (ObjectUtils.isNotEmpty(cachedCategoryDto)) {
            return cachedCategoryDto;
        }
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new SpringRedisServiceException(CATEGORY_NOT_FOUND_ERROR));
        CategoryDto categoryDto = CategoryDto.builder()
            .id(category.getId())
            .name(category.getName())
            .build();
        categoryCacheService.cacheCategory(categoryId, categoryDto);
        return categoryDto;
    }

    public void deleteCategory(final Long categoryId) {
        categoryRepository.deleteById(categoryId);
        categoryCacheService.evictCategoryCache(categoryId);
    }
}
