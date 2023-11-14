package tec.bd.weather.service;

import tec.bd.weather.entity.City;
import tec.bd.weather.entity.State;
import tec.bd.weather.repository.Repository;

import java.util.List;
import java.util.Optional;

public class CityServiceImpl implements CityService{

    private Repository<City, Integer> cityRepository;

    public CityServiceImpl(Repository<City, Integer> cityRepository) {
        this.cityRepository = cityRepository;
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
        // TODO:
        //logica no permitir que contryname sea nulo o vacio
        //VALIDAR si el country name ya existe ne la base de datos
        // ára esto abria que buscar el nombre del pais countryname en la base de datos
        //y ver si existe. si ya existe no se salva
        var cityToBeSave = new City(null,cityName,zipCode,stateId);
        var newCity = (this.cityRepository.save(cityToBeSave));
        return newCity;
    }

    @Override
    public void removeByCityId(int cityId){
        // Validaciones. El country Id es mayor que cero?
        // lanza una exception
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
        // TODO: validar que exista el state.country_id

        var cityUpdated = this.cityRepository.update(city);
        return cityUpdated;
    }
}
