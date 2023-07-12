package com.ibmproject.crops.exchanges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
public class GetCropsRecommendationRequest {
    @NotBlank
    private String season;
    @NotBlank
    private String state;
    @NotBlank
    private String district;
}
