package com.ibmproject.crops.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "weather-api")
@Data
public class WeatherAPIClientConfiguration {
    private String apiKey;

    private String baseUrl;
}
