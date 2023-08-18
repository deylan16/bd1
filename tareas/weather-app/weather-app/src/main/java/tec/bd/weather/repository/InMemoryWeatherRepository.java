package tec.bd.weather.repository;

import tec.bd.weather.entity.Weather;

import java.util.*;

public class InMemoryWeatherRepository implements Repository<Weather,Integer>{

    private Set<Weather> inMemoryWeatherData;

    public InMemoryWeatherRepository() {
        //Inicializando base de datos
        this.inMemoryWeatherData = new HashSet<Weather>();
        this.inMemoryWeatherData.add(new Weather(1,"Costa Rica" ,"Alajuela", "10101", 23.0f));
        this.inMemoryWeatherData.add(new Weather(2,"Costa Rica", "Cartago", "20201", 24.0f));
        this.inMemoryWeatherData.add(new Weather(3,"Costa Rica", "San Jose", "30301", 25.0f));
        this.inMemoryWeatherData.add(new Weather(4,"Costa Rica", "Limon", "40401", 26.0f));
    }

    @Override
    public Optional<Weather> findById(Integer id) {
        return this.inMemoryWeatherData
                .stream()
                .filter(e -> Objects.equals(e.getId(), id))
                .findFirst();

    }

    @Override
    public List<Weather> findAll() {
        return new ArrayList<>(this.inMemoryWeatherData);
    }

    @Override
    public void save(Weather weather) {
        this.inMemoryWeatherData.add(weather);
    }

    @Override
    public void delete(Integer id) {
        var weatherToDelete = this.findById(id);
        this.inMemoryWeatherData.remove(weatherToDelete.get());

    }

    @Override
    public Weather update(Weather source) {
        var current = this.findById(source.getId()).get();
        current.setCityName(source.getCityName());
        current.setZipCode(source.getZipCode());
        current.setTemperature(source.getTemperature());
        return current;
    }
}
