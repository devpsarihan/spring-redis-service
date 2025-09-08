package com.spring_redis_service.service;

import static com.spring_redis_service.configuration.redis.RedisConstant.PRODUCT_CACHE;

import com.spring_redis_service.advice.ErrorCode;
import com.spring_redis_service.advice.exception.SpringRedisServiceException;
import com.spring_redis_service.converter.ProductConverter;
import com.spring_redis_service.model.dto.CategoryDto;
import com.spring_redis_service.model.dto.ProductDto;
import com.spring_redis_service.model.entity.Category;
import com.spring_redis_service.model.entity.Product;
import com.spring_redis_service.model.request.CreateProductRequest;
import com.spring_redis_service.model.request.UpdateProductRequest;
import com.spring_redis_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final CategoryService categoryService;

    public Long createProduct(final CreateProductRequest request) {
        CategoryDto categoryDto = categoryService.getCategoryById(request.getCategoryId());
        return productRepository.save(
            Product.builder()
                .title(request.getTitle())
                .category(Category.builder().id(categoryDto.getId()).name(categoryDto.getName()).build())
                .price(request.getPrice())
                .count(request.getCount())
                .description(request.getDescription())
                .build()).getId();
    }

    @Cacheable(value = PRODUCT_CACHE, key = "#productId", unless = "#result == null")
    public ProductDto getProductById(final Long productId) {
        return productRepository.findById(productId).map(productConverter::convert)
            .orElseThrow(() -> new SpringRedisServiceException(ErrorCode.PRODUCT_NOT_FOUND_ERROR));
    }

    @CacheEvict(value = PRODUCT_CACHE, key = "#productId")
    public void deleteProduct(final Long productId) {
        productRepository.deleteById(productId);
    }

    @CachePut(value = PRODUCT_CACHE, key = "#productId", unless = "#result == null")
    public ProductDto updateProduct(final Long productId, final UpdateProductRequest request) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new SpringRedisServiceException(ErrorCode.PRODUCT_NOT_FOUND_ERROR));
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setCount(request.getCount());
        product.setPrice(request.getPrice());
        productRepository.save(product);
        return productConverter.convert(product);
    }
}
