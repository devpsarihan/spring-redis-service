package com.spring_redis_service.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.spring_redis_service.integration.TestContainersConfiguration;
import com.spring_redis_service.model.dto.ProductDto;
import com.spring_redis_service.model.request.CreateCategoryRequest;
import com.spring_redis_service.model.request.CreateProductRequest;
import com.spring_redis_service.model.request.UpdateProductRequest;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ProductControllerIntegrationTest extends TestContainersConfiguration {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void testCreateProduct_WhenValidCreateProductRequest_ShouldReturnProductId() {
        Long categoryId = createCategory("Book");
        Long productId = createProduct(categoryId);
        assertThat(productId).isNotNull();
    }

    @Test
    void testGetProduct_WhenExistProductId_ShouldReturnProduct() {
        Long categoryId = createCategory("Book");
        Long productId = createProduct(categoryId);
        ResponseEntity<ProductDto> response = testRestTemplate.getForEntity("/v1/products/" + productId,
            ProductDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getTitle()).isEqualTo("Clean Code");
    }

    @Test
    void testUpdateProduct_WhenValidUpdateProductRequest_ShouldReturnProduct() {
        Long categoryId = createCategory("Book");
        Long productId = createProduct(categoryId);

        UpdateProductRequest updateRequest = UpdateProductRequest.builder()
            .title("The Clean Coder")
            .categoryId(categoryId)
            .price(BigDecimal.valueOf(59.99))
            .count(15)
            .description("The Clean Coder: A Code of Conduct for Professional")
            .build();

        HttpEntity<UpdateProductRequest> entity = new HttpEntity<>(updateRequest);
        ResponseEntity<ProductDto> response = testRestTemplate.exchange(
            "/v1/products/" + productId, HttpMethod.PUT, entity, ProductDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getTitle()).contains("The Clean Coder");
    }

    @Test
    void testDeleteProduct_WhenExistProductId_ShouldSuccess() {
        Long categoryId = createCategory("Book");
        Long productId = createProduct(categoryId);
        testRestTemplate.delete("/v1/products/" + productId);
        ResponseEntity<String> response = testRestTemplate.getForEntity("/v1/products/" + productId, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private Long createCategory(String name) {
        CreateCategoryRequest request = CreateCategoryRequest.builder().name(name).build();
        return testRestTemplate.postForEntity("/v1/categories", request, Long.class).getBody();
    }

    private Long createProduct(Long categoryId) {
        CreateProductRequest request = CreateProductRequest.builder()
            .title("Clean Code")
            .categoryId(categoryId)
            .price(BigDecimal.valueOf(49.99))
            .count(10)
            .description("Clean Code: A Handbook of Agile Software Craftsmanship")
            .build();
        return testRestTemplate.postForEntity("/v1/products", request, Long.class).getBody();
    }
}
