package com.va.weather.service;

import com.va.weather.entity.WeatherEntity;
import com.va.weather.entity.WeatherEntityAverageProjection;
import com.va.weather.exception.IncorrectPeriodException;
import com.va.weather.repo.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class WeatherService {
    @Autowired
    private WeatherRepository repository;

    public WeatherEntity save(WeatherEntity entity) {
        try {
            return repository.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public WeatherEntity getCurrent() {
        return repository.findFirstByOrderByDateDesc();
    }

    public WeatherEntityAverageProjection getPeriod(String from, String to) throws IncorrectPeriodException {
        LocalDateTime endDate = parseDateFromString(to, LocalDateTime.now());
        LocalDateTime startDate = parseDateFromString(from, LocalDateTime.now().minusDays(1));

        if (startDate.isAfter(endDate)) throw new IncorrectPeriodException();

        return repository.findWeatherDuringLastDayNative(
                startDate.format(DateTimeFormatter.ISO_DATE_TIME),
                endDate.format(DateTimeFormatter.ISO_DATE_TIME)
        );
    }

    private LocalDateTime parseDateFromString(String dateString, LocalDateTime defaultValue) {
        LocalDateTime value = parseDateFromString(dateString);
        return value == null ? defaultValue : value;
    }

    private LocalDateTime parseDateFromString(String dateString) {
        try {
            if (dateString == null) return null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return LocalDateTime.of(LocalDate.parse(dateString, formatter), LocalTime.MIDNIGHT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
