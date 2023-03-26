package com.va.weather.service;

import com.va.weather.entity.WeatherEntity;
import com.va.weather.entity.WeatherEntityAverageProjection;
import com.va.weather.exception.IncorrectPeriodException;
import com.va.weather.repo.WeatherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {
    @Mock
    private WeatherRepository repository;
    @InjectMocks
    private WeatherService service;

    @Test
    void testSaveFromAPI() {
        WeatherEntity entity = new WeatherEntity();

        Mockito.when(repository.save(Mockito.any(WeatherEntity.class))).thenReturn(entity);

        WeatherEntity savedEntity = service.save(entity);

        assertNotNull(savedEntity);
    }

    @Test
    void testGetPeriod() throws IncorrectPeriodException {
        String from = "20-01-2023";
        String to = "22-03-2023";
        LocalDateTime startDate = LocalDateTime.of(2023, Month.JANUARY, 20, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, Month.MARCH, 22, 0, 0, 0);

        WeatherEntityAverageProjection projection = new WeatherEntityAverageProjection() {
            @Override
            public Float getAvgTemperature() {
                return 12f;
            }

            @Override
            public Integer getAvgPressure() {
                return 12;
            }

            @Override
            public Integer getAvgHumidity() {
                return 12;
            }

            @Override
            public Float getAvgWindSpeed() {
                return 12f;
            }
        };

        Mockito.when(
                repository.findWeatherDuringLastDayNative(
                    startDate.format(DateTimeFormatter.ISO_DATE_TIME),
                    endDate.format(DateTimeFormatter.ISO_DATE_TIME)
                )
                )
                .thenReturn(projection);

        WeatherEntityAverageProjection foundProjection = service.getPeriod(from, to);

        assertNotNull(foundProjection);
    }

    @Test
    void testExceptionGetPeriod() {
        String from = "20-03-2023";
        String to = "22-01-2023";

        assertThrows(IncorrectPeriodException.class, () -> service.getPeriod(from, to));
    }
}