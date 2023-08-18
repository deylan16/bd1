package tec.bd.weather.repository;

import tec.bd.weather.entity.Forecast;

import java.util.*;

public class InMemoryForecastRepository implements Repository<Forecast,Integer>{

    private Set<Forecast> inMemoryForecasData;

    public InMemoryForecastRepository() {
        // "inicializando" la base de datos
        this.inMemoryForecasData = new HashSet<>();
        this.inMemoryForecasData.add(new Forecast(1, "Costa Rica", "Alajuela", "10101", 23.0f));
        this.inMemoryForecasData.add(new Forecast(2, "Costa Rica", "Cartago", "20201", 24.0f));
        this.inMemoryForecasData.add(new Forecast(3, "Costa Rica", "San Jose", "30301", 25.0f));
        this.inMemoryForecasData.add(new Forecast(4, "Costa Rica", "Limon", "40401", 25.0f));
    }

    @Override
    public Optional<Forecast> findById(Integer id) {
        return this.inMemoryForecasData
                .stream()
                .filter(e -> Objects.equals(e.getId(), id))
                .findFirst();
    }

    @Override
    public List<Forecast> findAll() {
        return new ArrayList<>(this.inMemoryForecasData);
    }

    @Override
    public void save(Forecast weather) {
        this.inMemoryForecasData.add(weather);
    }

    @Override
    public void delete(Integer id) {
        var weatherToDelete = this.findById(id);
        this.inMemoryForecasData.remove(weatherToDelete.get());

    }

    @Override
    public Forecast update(Forecast source) {
        // source =  new Weather(1, "Alajuela", "10101", 30.0f)
        var current = this.findById(source.getId()).get();
        // current =  new Weather(1, "Alajuela", "10101", 23.0f)

        current.setCountryName(source.getCountryName());
        current.setCityName(source.getCityName());
        current.setZipCode(source.getZipCode());
        current.setTemperature(source.getTemperature());

        // borramos el objeto existente y lo reemplazamos por el actualizado
        this.delete(current.getId());
        this.save(current);

        return current;
        // source =  new Weather(1, "Alajuela", "10101", 30.0f)
    }
}
