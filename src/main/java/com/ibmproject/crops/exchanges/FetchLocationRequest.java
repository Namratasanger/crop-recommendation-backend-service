package com.ibmproject.crops.exchanges;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class FetchLocationRequest {
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
}
