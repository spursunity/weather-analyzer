package com.va.weather.service;

import com.va.weather.entity.WeatherEntity;
import com.va.weather.repo.WeatherRepository;
import com.va.weather.utils.WeatherFromAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class WeatherService {
    @Autowired
    private WeatherRepository repository;

    public void saveFromAPI(WeatherFromAPI weatherFromAPI) {
        try {
            WeatherEntity weatherEntity = new WeatherEntity();
            weatherEntity.setLocation(weatherFromAPI.getLocation().getName());
            weatherEntity.setTemperature(weatherFromAPI.getCurrent().getTemperature());
            weatherEntity.setWindSpeed(weatherFromAPI.getCurrent().getWindSpeed());
            weatherEntity.setPressure(weatherFromAPI.getCurrent().getPressure());
            weatherEntity.setHumidity(weatherFromAPI.getCurrent().getHumidity());
            weatherEntity.setConditionText(weatherFromAPI.getCurrent().getCondition().getCondition());
            weatherEntity.setDate(LocalDateTime.now(ZoneId.systemDefault()));

            repository.save(weatherEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WeatherEntity getCurrent() {
        return repository.findFirstByOrderByDateDesc();
    }
}
