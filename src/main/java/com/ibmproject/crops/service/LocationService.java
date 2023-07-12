package com.ibmproject.crops.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.ibmproject.crops.config.GeoCoderClientConfiguration;
import com.ibmproject.crops.dto.LocationDto;
import com.ibmproject.crops.exchanges.FetchLocationRequest;
import com.ibmproject.crops.exchanges.ModelResponse;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class LocationService {

    private final GeoCoderClientConfiguration config;

    private final RestTemplate restTemplate;

    private LoadingCache<FetchLocationRequest, LocationDto> cache;

    public LocationService(GeoCoderClientConfiguration config, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.config = config;
        this.cache = CacheBuilder
                .newBuilder()
                .refreshAfterWrite(5, TimeUnit.MINUTES)
                .build(cacheLoader());
    }

    private CacheLoader<FetchLocationRequest, LocationDto> cacheLoader() {
        return new CacheLoader<FetchLocationRequest, LocationDto>() {
            @Override
            public LocationDto load(FetchLocationRequest request) throws Exception {
                log.info("Fetching Location for ::: {}", request);
                return fetchLocation(request);
            }
        };
    }



    public LocationDto fetchLocation(FetchLocationRequest locationRequest) {
        String coords = String.format("%s+%s", locationRequest.getLatitude(), locationRequest.getLongitude());
        String apiKey = config.getApiKey();
        String uri = UriComponentsBuilder
                .fromUriString(config.getBaseUrl())
                .queryParam("key", apiKey)
                .queryParam("q", coords)
                .build()
                .toUriString();
        String response =  restTemplate.getForObject(uri, String.class);
        log.info(response);
        return mapToLocationDto(response);
    }

    public LocationDto getLocation(FetchLocationRequest locationRequest) {
        return cache.getUnchecked(locationRequest);
    }

    public LocationDto mapToLocationDto(String response) {
        DocumentContext context = JsonPath.parse(response);
        LocationDto location = context.read("$.results[0].components", LocationDto.class);
        String formattedAddress = context.read("$.results[0].formatted", String.class);
        location.setFormattedAddress(formattedAddress);
        return location;
    }
}
