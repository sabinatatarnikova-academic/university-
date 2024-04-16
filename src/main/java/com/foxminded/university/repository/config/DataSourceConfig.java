package com.foxminded.university.repository.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    private HikariConfig config = new HikariConfig();

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    @Profile("postgres")
    public DataSource dataSource() {
        config.setJdbcUrl(dbUrl);
        config.setUsername(username);
        config.setPassword(password);
        return new HikariDataSource(config);
    }

    @Bean
    @Profile("h2")
    public DataSource dataSourceH2() {
        config.setJdbcUrl("jdbc:h2:tcp://localhost/~/test");
        config.setUsername("sa");
        config.setPassword("");
        return new HikariDataSource(config);
    }
}
