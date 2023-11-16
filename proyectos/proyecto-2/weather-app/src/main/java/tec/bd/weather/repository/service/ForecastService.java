package tec.bd.weather.repository.service;

import tec.bd.weather.entity.City;
import tec.bd.weather.entity.Forecast;

import java.util.List;
import java.util.Optional;

public interface ForecastService {

    List<Forecast> getAllForecasts();

    Optional<Forecast> getForecastById(int forecastId);

    List<Forecast> getAllForecasts_Date();

    List<Forecast> getForecastByDate(java.sql.Date forecastDate);
    List<Forecast> getAllForecasts_Zip();

    List<Forecast>  getForecastByZip(int forecastZip);
    Forecast newForecast(int forecastName,int temperature,java.sql.Date date);

    void removeByForecastId(int forecastId);

    Forecast updateForecast(Forecast forecast);
    void initService( CityService cityRepository);
}
