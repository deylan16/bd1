package tec.bd.weather.service;

import tec.bd.weather.entity.Forecast;
import tec.bd.weather.repository.Repository;

public class WeatherServiceImpl implements WeatherService{
    private final Repository<Forecast, Integer> weatherRepository;
    //private Map<String, Float> cityTemperatureData;

    //private Map<String, Float> zipCodeTemperatureData;

    public WeatherServiceImpl(Repository<Forecast, Integer> weatherRepository){
        this.weatherRepository = weatherRepository;

    }



    @Override
    public float getByCityTemperature(String city) {
        var weather = this.weatherRepository
                .findAll()
                .stream()
                .filter(e -> e.getCityName().equals(city))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(city + " is not supported"));

        return weather.getTemperature();
    }

    @Override
    public float getByZipCodeTemperature(String zipCode) {
        var weather = this.weatherRepository
                .findAll()
                .stream()
                .filter(e -> e.getZipCode().equals(zipCode))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(zipCode + " is not supported"));

        return weather.getTemperature();
    }

    @Override
    public void newForecast(Forecast forecast) {
        forecast.validate(forecast);
        var current = this.weatherRepository.findById(forecast.getId());
        if (current.isPresent()) {
            throw new RuntimeException("Weather forecast ID already exists in database");
        }

        this.weatherRepository.save(forecast);
    }
    @Override
    public Forecast updateForecast(Forecast forecast) {
        Forecast.validate(forecast);
        var current = this.weatherRepository.findById(forecast.getId());
        if (current.isEmpty()) {
            throw new RuntimeException("Weather forecast ID doesn't exists in database");
        }
        return this.weatherRepository.update(forecast);
    }

    @Override
    public void removeForecast(int forecastId) {
        var current = this.weatherRepository.findById(forecastId);
        if (current.isEmpty()) {
            throw new RuntimeException("Weather forecast ID doesn't exists in database");
        }
        this.weatherRepository.delete(forecastId);
    }
}
