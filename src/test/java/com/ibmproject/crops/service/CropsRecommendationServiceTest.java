package com.ibmproject.crops.service;

import com.ibmproject.crops.dto.CropDto;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CropsRecommendationServiceTest {

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Autowired
    private CropsRecommendationService cropsService;

    @BeforeEach
    public void setup() {
        this.mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void getCurrentWeatherDetails() throws IOException {
        String baseUrl = "http://169.51.195.137:30002";

        String season = "Summer";
        String state = "Gujarat";
        String district = "Rajkot";

        String url = String.format("%s/predict?State=%s&Season=%s&District=%s", baseUrl, state, season, district);
        String mockResponse = FixtureHelper.getFileAsString("crops_api_response.json");
        mockServer.expect(ExpectedCount.once(),
                requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mockResponse));
        List<CropDto> crops = cropsService.getCropRecommendations(season, state, district);
        mockServer.verify();

        assertEquals(5, crops.size());
        assertEquals("Potato", crops.get(0).getCrop());
        assertEquals(13.0766667, crops.get(0).getYield());



    }

}