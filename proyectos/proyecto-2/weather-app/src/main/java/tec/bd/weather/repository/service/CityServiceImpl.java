package tec.bd.weather.repository.service;

import tec.bd.weather.entity.City;
import tec.bd.weather.entity.Forecast;
import tec.bd.weather.entity.State;
import tec.bd.weather.repository.Repository;

import java.util.List;
import java.util.Optional;

public class CityServiceImpl implements CityService{

    private Repository<City, Integer> cityRepository;
    private StateService stateRepository;

    private ForecastService forecastRepository;

    public CityServiceImpl(Repository<City, Integer> cityRepository) {
        this.cityRepository = cityRepository;

    }
    public void initService(StateService stateRepository,ForecastService forecastRepository){
        this.stateRepository = stateRepository;
        this.forecastRepository = forecastRepository;

    }

    @Override
    public List<City> getAllCitys() {
        return this.cityRepository.findAll();

    }

    @Override
    public Optional<City> getCityById(int cityId) {
        return this.cityRepository.findById(cityId);
    }

    @Override
    public City newCity(String cityName,int zipCode,int stateId) {

        for (City city : this.getAllCitys()) {
            if (zipCode == city.getZipCode()){
                throw new RuntimeException("Zip code already exist");
            }

        }
        for (State state : this.stateRepository.getAllStates()) {

            if (state.getId() != stateId){
                throw new RuntimeException("stateId id not exist");
            }

        }


        var cityToBeSave = new City(null,cityName,zipCode,stateId);
        var newCity = (this.cityRepository.save(cityToBeSave));
        return newCity;
    }

    @Override
    public void removeByCityId(int cityId){
        for (Forecast forecast : this.forecastRepository.getAllForecasts()) {
            if (forecast.getCity_id() == cityId){
                throw new RuntimeException("have forecast");
            }

        }
        if (cityId <= 0) {
            throw new RuntimeException("City Id requeride < 0");
        }
        var cityFromDBOpt = this.getCityById(cityId);

        if (cityFromDBOpt.isEmpty()) {
            throw new RuntimeException("City Id: " + cityId + " not found");
        }

        this.cityRepository.delete(cityId);
    }

    @Override
    public City updateCity(City city) {
        // TODO: validar si el state.Id existe en la base de datos
        // TODO: validar si el nombre del state ya existe en la BD
        for (City city2 : this.getAllCitys()) {
            if (city.getZipCode().equals(city2.getZipCode())){
                throw new RuntimeException("Zip code already exist");
            }

        }

        var cityUpdated = this.cityRepository.update(city);
        return cityUpdated;
    }
}
