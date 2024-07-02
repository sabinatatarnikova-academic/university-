package com.foxminded.university;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = {
        "com.foxminded.university"
})
public class TestConfig {

}
