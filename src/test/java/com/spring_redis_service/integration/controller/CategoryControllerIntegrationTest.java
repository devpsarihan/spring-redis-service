package com.spring_redis_service.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.spring_redis_service.integration.TestContainersConfiguration;
import com.spring_redis_service.model.dto.CategoryDto;
import com.spring_redis_service.model.request.CreateCategoryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class CategoryControllerIntegrationTest extends TestContainersConfiguration {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void testCreateCategory_WhenValidCreateCategoryRequest_ShouldReturnCategoryId() {
        CreateCategoryRequest request = CreateCategoryRequest.builder().name("Electronic").build();
        ResponseEntity<Long> response = testRestTemplate.postForEntity("/v1/categories", request, Long.class);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void testGetCategory_WhenValidCategoryId_ShouldReturnCategory() {
        CreateCategoryRequest request = CreateCategoryRequest.builder().name("Book").build();
        ResponseEntity<Long> categoryResponse = testRestTemplate.postForEntity("/v1/categories", request, Long.class);
        Long categoryId = categoryResponse.getBody();
        ResponseEntity<CategoryDto> response = testRestTemplate.getForEntity("/v1/categories/" + categoryId,
            CategoryDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("Book");
    }

    @Test
    void testDeleteCategory_WhenValidCategoryId_ShouldSuccess() {
        CreateCategoryRequest request = CreateCategoryRequest.builder().name("Toy").build();
        ResponseEntity<Long> categoryResponse = testRestTemplate.postForEntity("/v1/categories", request, Long.class);
        Long categoryId = categoryResponse.getBody();
        testRestTemplate.delete("/v1/categories/" + categoryId);
        ResponseEntity<String> response = testRestTemplate.getForEntity("/v1/categories/" + categoryId, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}

