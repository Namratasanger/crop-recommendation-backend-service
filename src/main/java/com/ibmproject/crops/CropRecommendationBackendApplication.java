package com.ibmproject.crops;

import com.ibmproject.crops.config.GeoCoderClientConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
@Log4j2
public class CropRecommendationBackendApplication{

  private final GeoCoderClientConfiguration config;
  public static void main(String[] args) {
    SpringApplication.run(CropRecommendationBackendApplication.class, args);
  }


}
