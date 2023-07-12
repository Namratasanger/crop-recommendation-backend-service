package com.ibmproject.crops.exchanges;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class ModelResponse {
    private Map<String, Double> predictions;
    @NotNull
    private boolean success;
}
