package com.ibmproject.crops.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class CropDto {
    private double yield;
    private String crop;
}
