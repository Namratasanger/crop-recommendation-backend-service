package com.ibmproject.crops.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "geocoder")
@Data
public class GeoCoderClientConfiguration {
    private String apiKey;

    private String baseUrl;
}
