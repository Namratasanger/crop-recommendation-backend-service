package com.ibmproject.crops.service;

import com.ibmproject.crops.dto.WeatherDto;
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

;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class WeatherServiceTest {

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Autowired
    private WeatherService weatherService;

    @BeforeEach
    public void setup() {
        this.mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void getCurrentWeatherDetails() throws IOException {
        String baseUrl = "http://api.weatherapi.com/v1";
        String apiKey = "192aa22b08d44f8d89f101548211403";
        double latitude = 20;
        double longitude = 70;
        String coords = String.format("%s,%s", latitude, longitude);
        String url = String.format("%s/current.json?key=%s&q=%s", baseUrl, apiKey, coords);
        String mockResponse = FixtureHelper.getFileAsString("weather_api_response.json");
        mockServer.expect(ExpectedCount.once(),
                requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mockResponse));
        WeatherDto currentWeather = weatherService.getCurrentWeatherDetails(latitude, longitude);
        mockServer.verify();
        assertEquals("Sunny", currentWeather.getCondition().getText());
        assertEquals(false, currentWeather.getIsDay());
        assertEquals(38, currentWeather.getTempC());

    }

//    @Test
//    void getCurrentWeatherDetails() throws IOException {
//        String baseUrl = "http://api.weatherapi.com/v1";
//        String apiKey = "192aa22b08d44f8d89f101548211403";
//        WeatherAPIClientConfiguration config = new WeatherAPIClientConfiguration();
//        config.setApiKey(apiKey);
//        config.setBaseUrl(baseUrl);
//        this.weatherService = new WeatherService(config, restTemplate);
//
////        when(config.getBaseUrl())
////                .thenReturn(baseUrl);
////        when(config.getApiKey())
////                .thenReturn(apiKey);
//
//
//        double latitude = 20;
//        double longitude = 70;
//        String coords = String.format("%s,%s", latitude, longitude);
//        String url = String.format("%s/current.json?key=%s&q=%s", baseUrl, apiKey, coords);
//        String mockResponse = FixtureHelper.getFileAsString("weather_api_response.json");
//        when(restTemplate.getForObject(url, String.class))
//                .thenReturn(mockResponse);
//        ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);
//
//        WeatherDto currentWeather = weatherService.fetchWeather(coords);
//
//
//        verify(restTemplate).getForObject(urlCaptor.capture(), eq(String.class));
//
//        assertEquals(url, urlCaptor.getValue());
//
//
//
//    }

}