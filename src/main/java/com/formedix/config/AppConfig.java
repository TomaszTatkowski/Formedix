package com.formedix.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = { "com.formedix.*" })
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${data.source.path}")
    private String dataSource;

    public String getDataSource() {
        return dataSource;
    }
}
