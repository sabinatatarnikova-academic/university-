package com.foxminded.university.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = {
        "com.foxminded.university.service",
        "com.foxminded.university.utils",
        "com.foxminded.university.config"
})
public class TestConfig {

}
