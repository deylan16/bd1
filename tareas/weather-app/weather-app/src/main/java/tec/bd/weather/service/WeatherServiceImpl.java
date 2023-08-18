package tec.bd.weather.service;

import tec.bd.weather.entity.Weather;
import tec.bd.weather.repository.Repository;

import java.util.HashMap;
import java.util.Map;
import static  java.util.Optional.*;

public class WeatherServiceImpl implements WeatherService{
    private final Repository<Weather, Integer> weatherRepository;
    //private Map<String, Float> cityTemperatureData;

    //private Map<String, Float> zipCodeTemperatureData;

    public WeatherServiceImpl(Repository<Weather, Integer> weatherRepository){
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
    public void newForecast(Weather weather) {
        weather.validate(weather);
        var current = this.weatherRepository.findById(weather.getId());
        if (current.isPresent()) {
            throw new RuntimeException("Weather forecast ID already exists in database");
        }

        this.weatherRepository.save(weather);

    }
}
