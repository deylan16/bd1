package tec.bd.weather.service;

import tec.bd.weather.entity.Forecast;
import java.util.List;
public interface WeatherService {
    float getByCityTemperature(String city);

    float getByZipCodeTemperature(String zipCode);

    List<Forecast> getAllForecasts();

    Forecast newForecast(Forecast weather);

    Forecast updateForecast(Forecast weather);

    void removeForecast(int forecastId);
}
