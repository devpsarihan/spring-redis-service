package com.spring_redis_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableConfigurationProperties
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
public class SpringRedisServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRedisServiceApplication.class, args);
    }

}
