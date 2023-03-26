package com.va.weather.repo;

import com.va.weather.entity.WeatherEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WeatherRepositoryTest {
    @Autowired
    private WeatherRepository weatherRepository;
    private final String lastLocationName = "London";

    @BeforeEach
    public void init() {
        WeatherEntity firstEntity = new WeatherEntity();
        firstEntity.setDate(LocalDateTime.now(ZoneId.systemDefault()));
        firstEntity.setLocation(lastLocationName);

        WeatherEntity secondEntity = new WeatherEntity();
        secondEntity.setDate(LocalDateTime.now(ZoneId.systemDefault()).minusHours(23));

        WeatherEntity thirdEntity = new WeatherEntity();
        thirdEntity.setDate(LocalDateTime.now(ZoneId.systemDefault()).minusDays(2));

        weatherRepository.save(firstEntity);
        weatherRepository.save(secondEntity);
        weatherRepository.save(thirdEntity);
    }

    @AfterEach
    public void drop() {
        weatherRepository.deleteAll();
    }

    @Test
    public void testSave() {
        String location = "Paris";
        WeatherEntity entity = new WeatherEntity();
        entity.setLocation(location);
        weatherRepository.save(entity);

        WeatherEntity result = weatherRepository.findById(entity.getId()).orElse(null);

        assertNotNull(result);
        assertEquals(location, result.getLocation());
        assertTrue(result.getId() > 0);
    }

    @Test
    public void testFindFirstByOrderByDateDesc() {
        WeatherEntity result = weatherRepository.findFirstByOrderByDateDesc();

        assertNotNull(result);
        assertEquals(lastLocationName, result.getLocation());
        assertFalse(result.getDate().isAfter(LocalDateTime.now(ZoneId.systemDefault())));
    }
}
