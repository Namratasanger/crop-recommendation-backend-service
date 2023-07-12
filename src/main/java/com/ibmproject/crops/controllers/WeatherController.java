package com.ibmproject.crops.controllers;


import com.ibmproject.crops.dto.LocationDto;
import com.ibmproject.crops.dto.WeatherDto;
import com.ibmproject.crops.exchanges.FetchLocationRequest;
import com.ibmproject.crops.service.LocationService;
import com.ibmproject.crops.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/current")
    public ResponseEntity<WeatherDto> getLocation(@RequestParam Double latitude, @RequestParam Double longitude) {
        return ResponseEntity.ok(weatherService.getCurrentWeatherDetails(latitude, longitude));
    }
}
