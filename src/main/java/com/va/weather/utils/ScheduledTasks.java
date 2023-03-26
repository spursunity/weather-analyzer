package com.va.weather.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.va.weather.entity.WeatherEntity;
import com.va.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class ScheduledTasks {
    @Autowired
    private WeatherService weatherService;
    private final long delay = 1000 * 60 * 60;
    @Value("${api.weather.url}")
    private final String url;
    @Value("${api.weather.header.key.name}")
    private final String apiHeaderKeyName;
    @Value("${api.weather.header.key.value}")
    private final String apiHeaderKeyValue;
    @Value("${api.weather.header.host.name}")
    private final String apiHeaderHostName;
    @Value("${api.weather.header.host.value}")
    private final String apiHeaderHostValue;

    public ScheduledTasks() {
        url = null;
        apiHeaderKeyName = null;
        apiHeaderHostName = null;
        apiHeaderKeyValue = null;
        apiHeaderHostValue = null;
    }

    @Scheduled(fixedDelay = delay)
    public void getWeatherDataFromAPI() {
        try {
            if (url != null) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header(apiHeaderKeyName, apiHeaderKeyValue)
                        .header(apiHeaderHostName, apiHeaderHostValue)
                        .method("GET", HttpRequest.BodyPublishers.noBody())
                        .build();

                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

                ObjectMapper objectMapper = new ObjectMapper();
                WeatherFromAPI weather = objectMapper.readValue(response.body(), WeatherFromAPI.class);
                WeatherEntity entity = parseEntityFromAPI(weather);
                weatherService.save(entity);
            }
        } catch (Exception e) {
            System.out.println("Check your 'apiHeaderKeyValue' (rapid API key value), please");
            e.printStackTrace();
        }
    }

    private WeatherEntity parseEntityFromAPI(WeatherFromAPI weatherFromAPI) {
        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setLocation(weatherFromAPI.getLocation().getName());
        weatherEntity.setTemperature(weatherFromAPI.getCurrent().getTemperature());
        weatherEntity.setWindSpeed(weatherFromAPI.getCurrent().getWindSpeed());
        weatherEntity.setPressure(weatherFromAPI.getCurrent().getPressure());
        weatherEntity.setHumidity(weatherFromAPI.getCurrent().getHumidity());
        weatherEntity.setConditionText(weatherFromAPI.getCurrent().getCondition().getCondition());
        weatherEntity.setDate(LocalDateTime.now(ZoneId.systemDefault()));

        return weatherEntity;
    }
}
