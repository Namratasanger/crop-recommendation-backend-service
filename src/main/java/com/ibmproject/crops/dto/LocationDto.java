package com.ibmproject.crops.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LocationDto {
    private String city;
    private String continent;
    private String country;
    private String countryCode;
    private String county;
    private String state;
    private String stateCode;
    private String stateDistrict;
    private String suburb;
    private String formattedAddress;
}
