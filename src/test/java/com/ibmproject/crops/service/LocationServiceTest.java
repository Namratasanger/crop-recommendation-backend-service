package com.ibmproject.crops.service;

import com.ibmproject.crops.dto.LocationDto;
import com.ibmproject.crops.dto.WeatherDto;
import com.ibmproject.crops.exchanges.FetchLocationRequest;
import com.ibmproject.crops.util.FixtureHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;



@ExtendWith(SpringExtension.class)
@SpringBootTest
class LocationServiceTest {

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Autowired
    private LocationService locationService;

    @BeforeEach
    public void setup() {
        this.mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void getCurrentWeatherDetails() throws IOException {
        String baseUrl = "https://api.opencagedata.com/geocode/v1/json";
        String apiKey = "15d367a2e4bb4442bc38ee110cddb977";
        double latitude = 22.9989879;
        double longitude = 72.5931222;
        FetchLocationRequest request = new FetchLocationRequest(latitude, longitude);
        String coords = String.format("%s+%s", latitude, longitude);
        String url = String.format("%s?key=%s&q=%s", baseUrl, apiKey, coords);
        String mockResponse = FixtureHelper.getFileAsString("location_api_response.json");
        mockServer.expect(ExpectedCount.once(),
                requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mockResponse));
        LocationDto currentLocation = locationService.getLocation(request);
        mockServer.verify();
        assertEquals("Ahmedabad", currentLocation.getCity());
        assertEquals("Gujarat", currentLocation.getState());
        assertEquals("India", currentLocation.getCountry());
        assertEquals("Ahmedabad District", currentLocation.getStateDistrict());


    }

}