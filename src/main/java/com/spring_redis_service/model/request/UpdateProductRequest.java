package com.spring_redis_service.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class UpdateProductRequest {

    @NotEmpty(message = "Title must not be empty")
    private String title;
    @NotNull(message = "Category id mus not be null")
    private Long categoryId;
    @NotNull(message = "Price must not be null")
    private BigDecimal price;
    @NotNull(message = "Count must not be null")
    private Integer count;
    @NotEmpty(message = "Description must not be empty")
    private String description;
}
