package com.ibmproject.crops.controllers;

import com.ibmproject.crops.exchanges.WelcomeResponse;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Slf4j
public class WelcomeController {
  @GetMapping
  public WelcomeResponse welcome(@RequestParam(required = false) String name){
    log.info("welcome controller");
    return new WelcomeResponse(name, String.format("Welcome!! %s", name));
  }
}
