package com.va.weather.repo;

import com.va.weather.entity.WeatherEntity;
import com.va.weather.entity.WeatherEntityAverageProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface WeatherRepository extends CrudRepository<WeatherEntity, Long> {
    WeatherEntity findFirstByOrderByDateDesc();
    @Query(
            value = "SELECT" +
                    " AVG(w.temperature) AS `avgTemperature`," +
                    " AVG(w.pressure) AS `avgPressure`," +
                    " AVG(w.humidity) AS `avgHumidity`," +
                    " AVG(w.wind_speed) AS `avgWindSpeed`" +
                    " FROM weatherDB.weather w WHERE w.date BETWEEN ?1 AND ?2",
            nativeQuery = true
    )
    WeatherEntityAverageProjection findWeatherDuringLastDayNative(String start, String end);
}
