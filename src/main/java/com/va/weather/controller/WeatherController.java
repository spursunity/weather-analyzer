package com.va.weather.controller;

import com.va.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/current")
    public ResponseEntity getCurrentWeather() {
        try {
            return ResponseEntity.ok(weatherService.getCurrent());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Sorry, cannot provide current weather data");
        }
    }
}
