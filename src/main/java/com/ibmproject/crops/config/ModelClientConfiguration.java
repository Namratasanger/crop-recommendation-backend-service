package com.ibmproject.crops.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "crop-model")
@Data
public class ModelClientConfiguration {
    private String baseUrl;
}
