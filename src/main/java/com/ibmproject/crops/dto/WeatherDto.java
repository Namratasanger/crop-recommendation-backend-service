package com.ibmproject.crops.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WeatherDto {
    private Float tempC;
    private Float tempF;
    private Boolean isDay;
    private WeatherCondition condition;
    private Float windKph;
    private String windDir;
    private String humidity;
}
