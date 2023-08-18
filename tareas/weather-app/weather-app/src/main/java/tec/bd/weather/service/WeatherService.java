package tec.bd.weather.service;

import tec.bd.weather.entity.Weather;

public interface WeatherService {
    float getByCityTemperature(String city);

    float getByZipCodeTemperature(String zipCode);

    void newForecast(Weather weather);
}
