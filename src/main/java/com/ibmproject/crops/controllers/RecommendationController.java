package com.ibmproject.crops.controllers;

import com.ibmproject.crops.dto.CropDto;
import com.ibmproject.crops.exchanges.GetCropsRecommendationRequest;
import com.ibmproject.crops.service.CropsRecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/crops")
@RequiredArgsConstructor
@Slf4j
public class RecommendationController {
    private final CropsRecommendationService cropService;
    @PostMapping("recommend")
    public ResponseEntity<List<CropDto>> cropRecommendations (@RequestBody @Valid GetCropsRecommendationRequest request){
        log.info("Crop Recommendation");
        if(request.getDistrict().equals("Ahmedabad")){
            request.setDistrict("Ahmadabad");
        }
        List<CropDto> cropRecommendations = cropService.getCropRecommendations(
                request.getSeason(),
                request.getState(),
                request.getDistrict()
        );
        return ResponseEntity.ok(cropRecommendations);
    }
}
