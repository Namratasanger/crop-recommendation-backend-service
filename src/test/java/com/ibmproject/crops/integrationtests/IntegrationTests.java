package com.ibmproject.crops.integrationtests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ibmproject.crops.dto.CropDto;
import com.ibmproject.crops.dto.LocationDto;
import com.ibmproject.crops.dto.WeatherDto;
import com.ibmproject.crops.exchanges.GetCropsRecommendationRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void locationApiIntegrationTest() {
        String uri = UriComponentsBuilder.fromUriString("http://localhost:" + port + "/api/v1/locations/get-address")
                .queryParam("latitude", 22.99895)
                .queryParam("longitude", 72.59305)
                .build().toUriString();
        LocationDto location = restTemplate.getForObject(uri, LocationDto.class);
        Assertions.assertEquals("Ahmedabad", location.getCity());
        Assertions.assertEquals("Gujarat", location.getState());
        Assertions.assertEquals("India", location.getCountry());
        Assertions.assertEquals("Ahmedabad District", location.getStateDistrict());
    }
    @Test
    public void weatherApiIntegrationTest() {
        String uri = UriComponentsBuilder.fromUriString("http://localhost:" + port + "/api/v1/weather/current")
                .queryParam("latitude", 22.99895)
                .queryParam("longitude", 72.59305)
                .build().toUriString();
        WeatherDto weather = restTemplate.getForObject(uri, WeatherDto.class);
        Assertions.assertNotNull(weather);
    }
    @Test
    public void cropsApiIntegrationTest() {
        String uri = UriComponentsBuilder.fromUriString("http://localhost:" + port + "/api/v1/crops/recommend")
                .build().toUriString();
        GetCropsRecommendationRequest request = new GetCropsRecommendationRequest();
        request.setDistrict("Rajkot");
        request.setState("Gujarat");
        request.setSeason("Summer");
        List<Map> crops = restTemplate.postForObject(uri, request, List.class);
        Assertions.assertEquals(5, crops.size());
        Assertions.assertEquals("Potato", crops.get(0).get("crop"));
        Assertions.assertEquals(13.0766667, crops.get(0).get("yield"));
    }

}
