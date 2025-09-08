package com.spring_redis_service.converter;

import com.spring_redis_service.model.dto.ProductDto;
import com.spring_redis_service.model.entity.Product;
import java.time.ZoneOffset;
import org.springframework.stereotype.Component;

@Component("productConverter")
public class ProductConverter {

    public ProductDto convert(final Product product) {
        return ProductDto.builder()
            .id(product.getId())
            .title(product.getTitle())
            .categoryId(product.getCategory().getId())
            .categoryName(product.getCategory().getName())
            .price(product.getPrice())
            .count(product.getCount())
            .description(product.getDescription())
            .createdDate(product.getCreatedDate().toInstant(ZoneOffset.UTC).toEpochMilli())
            .modifiedDate(product.getModifiedDate().toInstant(ZoneOffset.UTC).toEpochMilli())
            .build();
    }
}
