package com.spring_redis_service.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductDto implements Serializable {

    private Long id;
    private String title;
    private BigDecimal price;
    private Long categoryId;
    private String categoryName;
    private Integer count;
    private String description;
    private Long createdDate;
    private Long modifiedDate;
}
