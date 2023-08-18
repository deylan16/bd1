package tec.bd.weather.service;

import tec.bd.weather.entity.Forecast;

public interface WeatherService {
    float getByCityTemperature(String city);

    float getByZipCodeTemperature(String zipCode);

    void newForecast(Forecast forecast);

    Forecast updateForecast(Forecast weather);

    void removeForecast(int forecastId);
}
