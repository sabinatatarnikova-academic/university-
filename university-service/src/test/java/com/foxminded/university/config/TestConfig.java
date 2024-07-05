package com.foxminded.university.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {
        "com.foxminded.university.service",
        "com.foxminded.university.repository",
        "com.foxminded.university.utils",
        "com.foxminded.university.config"
})
@EntityScan(basePackages = "com.foxminded.university.model")
@EnableJpaRepositories(basePackages = "com.foxminded.university.repository")
public class TestConfig {
}
