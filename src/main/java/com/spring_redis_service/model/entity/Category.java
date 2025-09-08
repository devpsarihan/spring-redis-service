package com.spring_redis_service.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(schema = "PRODUCT", name = "CATEGORY")
@EqualsAndHashCode(callSuper = true)
public class Category extends BaseEntity {

    @Column(name = "NAME")
    private String name;
}
