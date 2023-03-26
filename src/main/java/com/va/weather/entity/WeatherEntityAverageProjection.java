package com.va.weather.entity;

public interface WeatherEntityAverageProjection {
    Float getAvgTemperature();
    Integer getAvgPressure();
    Integer getAvgHumidity();
    Float getAvgWindSpeed();
}
