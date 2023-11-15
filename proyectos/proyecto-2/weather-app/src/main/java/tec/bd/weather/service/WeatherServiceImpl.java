package tec.bd.weather.service;

import tec.bd.weather.entity.ForecastAnterior;
import tec.bd.weather.repository.Repository;

import java.util.List;

public class WeatherServiceImpl implements WeatherService{
    private final Repository<ForecastAnterior, Integer> weatherRepository;
    //private Map<String, Float> cityTemperatureData;

    //private Map<String, Float> zipCodeTemperatureData;

    public WeatherServiceImpl(Repository<ForecastAnterior, Integer> weatherRepository){
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
    public List<ForecastAnterior> getAllForecasts() {
        // TODO: aqui podria ir logica de conversion de tipos

        return this.weatherRepository.findAll();
    }

    @Override
    public ForecastAnterior newForecast(ForecastAnterior newForecastAnterior) {
        ForecastAnterior.validate(newForecastAnterior);
        var current = this.weatherRepository.findById(newForecastAnterior.getId());
        if (current.isPresent()) {
            throw new RuntimeException("Weather forecast ID already exists in database");
        }

        return this.weatherRepository.save(newForecastAnterior);
    }
    @Override
    public ForecastAnterior updateForecast(ForecastAnterior forecastAnterior) {
        ForecastAnterior.validate(forecastAnterior);
        if (forecastAnterior.getId() < 1) {
            throw new RuntimeException("Invalid forecast Id " + forecastAnterior.getId());
        }
        var current = this.weatherRepository.findById(forecastAnterior.getId());
        if (current.isEmpty()) {
            throw new RuntimeException("Weather forecast ID doesn't exists in database");
        }
        return this.weatherRepository.update(forecastAnterior);
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
