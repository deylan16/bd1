package tec.bd.weather.repository.service;

import tec.bd.weather.entity.City;
import tec.bd.weather.entity.Forecast;
import tec.bd.weather.repository.Repository;
import tec.bd.weather.repository.sql.ForecastRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class ForecastServiceImpl implements ForecastService{

    private ForecastRepository forecastRepository;
    private CityService cityRepository;

    public ForecastServiceImpl(ForecastRepository forecastRepository) {
        this.forecastRepository = forecastRepository;

    }

    public void initService( CityService cityRepository){
        this.cityRepository = cityRepository;
    }

    @Override
    public List<Forecast> getAllForecasts() {
        return this.forecastRepository.findAll();

    }

    @Override
    public Optional<Forecast> getForecastById(int forecastId) {
        return this.forecastRepository.findById(forecastId);
    }

    @Override
    public List<Forecast> getAllForecasts_Date() {
        return this.forecastRepository.findAll_Date();
    }

    @Override
    public List<Forecast> getForecastByDate(Date forecastDate) {
        return this.forecastRepository.findByDate(forecastDate);
    }

    @Override
    public List<Forecast> getAllForecasts_Zip() {
        return this.forecastRepository.findAll_Zip();
    }

    @Override
    public List<Forecast>  getForecastByZip(int forecastZip) {
        return this.forecastRepository.findByZip(forecastZip);
    }

    @Override
    public Forecast newForecast(int cityCode,int temperature,java.sql.Date  date) {

        for (City city : this.cityRepository.getAllCitys()) {

            if (city.getId() != cityCode){
                throw new RuntimeException("City id not exist");
            }

        }


        var forecastToBeSave = new Forecast(null,cityCode,temperature,date);
        var newForecast = (this.forecastRepository.save(forecastToBeSave));
        return newForecast;
    }

    @Override
    public void removeByForecastId(int forecastId){
        // Validaciones. El country Id es mayor que cero?
        // lanza una exception
        if (forecastId <= 0) {
            throw new RuntimeException("Forecast Id requeride < 0");
        }
        var forecastFromDBOpt = this.getForecastById(forecastId);

        if (forecastFromDBOpt.isEmpty()) {
            throw new RuntimeException("Forecast Id: " + forecastId + " not found");
        }

        this.forecastRepository.delete(forecastId);
    }

    @Override
    public Forecast updateForecast(Forecast forecast) {
        // TODO: validar si el state.Id existe en la base de datos
        // TODO: validar si el nombre del state ya existe en la BD
        // TODO: validar que exista el state.country_id

        var forecastUpdated = this.forecastRepository.update(forecast);
        return forecastUpdated;
    }
}
