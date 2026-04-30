package com.abdulmo123.void_user.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@EnableConfigurationProperties
public class AppProperties {

    @Value("${app.url}")
    private String appUrl;

    @Value("${app.dummy-email}")
    private String dummyEmail;
}
