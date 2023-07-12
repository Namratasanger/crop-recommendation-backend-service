package com.ibmproject.crops.controllers;

import com.ibmproject.crops.dto.LocationDto;
import com.ibmproject.crops.exchanges.FetchLocationRequest;
import com.ibmproject.crops.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/locations")
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/get-address")
    public ResponseEntity<LocationDto> getLocation(@RequestParam Double latitude, @RequestParam Double longitude) {
        return ResponseEntity.ok(locationService.getLocation(new FetchLocationRequest(latitude, longitude)));
    }
}
