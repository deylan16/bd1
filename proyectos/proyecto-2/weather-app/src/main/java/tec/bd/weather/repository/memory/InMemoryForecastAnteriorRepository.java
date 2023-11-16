package tec.bd.weather.repository.memory;

import tec.bd.weather.entity.ForecastAnterior;
import tec.bd.weather.repository.Repository;

import java.util.*;

public class InMemoryForecastAnteriorRepository implements Repository<ForecastAnterior, Integer> {

    private Set<ForecastAnterior> inMemoryForecastDatumAnteriors;

    public InMemoryForecastAnteriorRepository() {
        // "inicializando" la base de datos
        this.inMemoryForecastDatumAnteriors = new HashSet<>();
        this.inMemoryForecastDatumAnteriors.add(new ForecastAnterior(1, "Costa Rica", "Alajuela", "10101", new Date(), 23.0f));
        this.inMemoryForecastDatumAnteriors.add(new ForecastAnterior(2, "Costa Rica", "Cartago", "20201", new Date(), 24.0f));
        this.inMemoryForecastDatumAnteriors.add(new ForecastAnterior(3, "Costa Rica", "San Jose", "30301", new Date(), 25.0f));
        this.inMemoryForecastDatumAnteriors.add(new ForecastAnterior(4, "Costa Rica", "Limon", "40401", new Date(), 25.0f));
    }

    public int getCurrentMaxId() {
        return this.inMemoryForecastDatumAnteriors
                .stream()
                .max(Comparator.comparing(ForecastAnterior::getId))
                .map(ForecastAnterior::getId)
                .orElse(0);
    }

    @Override
    public Optional<ForecastAnterior> findById(Integer id) {
        return this.inMemoryForecastDatumAnteriors
                .stream()
                .filter(e -> Objects.equals(e.getId(), id))
                .findFirst();
    }

    @Override
    public List<ForecastAnterior> findAll() {
        return new ArrayList<>(this.inMemoryForecastDatumAnteriors);
    }

    @Override
    public ForecastAnterior save(ForecastAnterior forecastAnterior) {
        forecastAnterior.setId(this.getCurrentMaxId() + 1);
        this.inMemoryForecastDatumAnteriors.add(forecastAnterior);
        return forecastAnterior;
    }

    @Override
    public void delete(Integer id) {
        System.out.println("En memoria");
        var weatherToDelete = this.findById(id);
        this.inMemoryForecastDatumAnteriors.remove(weatherToDelete.get());
    }

    @Override
    public ForecastAnterior update(ForecastAnterior source) {
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

