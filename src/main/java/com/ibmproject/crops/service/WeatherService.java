package com.ibmproject.crops.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.ibmproject.crops.config.WeatherAPIClientConfiguration;
import com.ibmproject.crops.dto.WeatherDto;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class WeatherService {

    private final WeatherAPIClientConfiguration config;

    private final LoadingCache<String, WeatherDto> cache;


    private final RestTemplate restTemplate;

    public WeatherService(WeatherAPIClientConfiguration config, RestTemplate restTemplate) {
        this.config = config;
        this.restTemplate = restTemplate;
        this.cache = CacheBuilder
                .newBuilder()
                .refreshAfterWrite(5, TimeUnit.MINUTES)
                .build(cacheLoader());

    }

    public CacheLoader<String, WeatherDto> cacheLoader() {
        return new CacheLoader<String, WeatherDto>() {
            @Override
            public WeatherDto load(String coords) throws Exception {
                log.info("Fetching Weather for ::: {}", coords);
                return fetchWeather(coords);
            }
        };
    }


    public WeatherDto fetchWeather(String coords) {
        String apiKey = config.getApiKey();
        String uri = UriComponentsBuilder
                .fromUriString(config.getBaseUrl() + "/current.json")
                .queryParam("key", apiKey)
                .queryParam("q", coords)
                .build()
                .toUriString();
        String response = restTemplate.getForObject(uri, String.class);
        log.info(response);
        return mapToWeatherDto(response);
    }

    public WeatherDto getCurrentWeatherDetails(double latitude, double longitude) {
        String coords = String.format("%s,%s", latitude, longitude);
        return this.cache.getUnchecked(coords);
    }

    public WeatherDto mapToWeatherDto(String response) {
        DocumentContext context = JsonPath.parse(response);
        return context.read("$.current", WeatherDto.class);
    }
}
