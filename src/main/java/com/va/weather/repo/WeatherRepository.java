package com.va.weather.repo;

import com.va.weather.entity.WeatherEntity;
import org.springframework.data.repository.CrudRepository;

public interface WeatherRepository extends CrudRepository<WeatherEntity, Long> {
}
