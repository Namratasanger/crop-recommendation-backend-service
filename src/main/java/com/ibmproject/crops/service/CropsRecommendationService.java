package com.ibmproject.crops.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.ibmproject.crops.config.ModelClientConfiguration;
import com.ibmproject.crops.dto.CropDto;
import com.ibmproject.crops.exchanges.ModelResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CropsRecommendationService {

    private final ModelClientConfiguration config;
    private final RestTemplate restTemplate;

    private LoadingCache<String, ModelResponse> cache;

    public CropsRecommendationService(ModelClientConfiguration config, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.config = config;
        this.cache = CacheBuilder
                .newBuilder()
                .refreshAfterWrite(30, TimeUnit.MINUTES)
                .build(cacheLoader());
    }



    public List<CropDto> getCropRecommendations(String season, String state, String district) {

        String query = season + "-" + state + "-" + district;
        ModelResponse modelResponse = cache.getUnchecked(query);
        if (modelResponse.isSuccess()) {
            return listOfCrops(modelResponse);
        }
        return Arrays.asList(
//                new CropDto(1.4, "Arecanut"),
//                new CropDto(1.4, "Black pepper"),
//                new CropDto(6.220381088, "Coconut "),
//                new CropDto(13.43610548, "Ginger"),
//                new CropDto(2.039312039, "Rice")
        );
    }

    private CacheLoader<String, ModelResponse> cacheLoader() {
        return new CacheLoader<String, ModelResponse>() {
            @Override
            public ModelResponse load(String query) throws Exception {
                String[] s = query.split("-");
                log.info("Fetching recommendations for ::: {}", query);
                return fetchRecommendations(s[0], s[1], s[2]);
            }
        };
    }

    public ModelResponse fetchRecommendations(String season, String state, String district) {
        String uri = UriComponentsBuilder
                .fromUriString(config.getBaseUrl() + "/predict")
                .queryParam("State", state)
                .queryParam("Season", season)
                .queryParam("District", district)
                .build()
                .toUriString();
        return restTemplate.getForObject(uri, ModelResponse.class);
    }

    public CropDto mapToCropDto(Map.Entry<String, Double> entry) {
        return new CropDto(entry.getValue(), entry.getKey());
    }

    public List<CropDto> listOfCrops(ModelResponse modelResponse) {
        return modelResponse.getPredictions()
                .entrySet()
                .stream()
                .map(this::mapToCropDto)
                .sorted(Comparator.comparingDouble(CropDto::getYield).reversed())
                .limit(5)
                .collect(Collectors.toList());

    }
}
