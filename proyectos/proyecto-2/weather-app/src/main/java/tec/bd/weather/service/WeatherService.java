package tec.bd.weather.service;

import tec.bd.weather.entity.ForecastAnterior;
import java.util.List;
public interface WeatherService {
    float getByCityTemperature(String city);

    float getByZipCodeTemperature(String zipCode);

    List<ForecastAnterior> getAllForecasts();

    ForecastAnterior newForecast(ForecastAnterior weather);

    ForecastAnterior updateForecast(ForecastAnterior weather);

    void removeForecast(int forecastId);
}
