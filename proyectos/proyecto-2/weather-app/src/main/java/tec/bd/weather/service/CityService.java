package tec.bd.weather.service;

import tec.bd.weather.entity.City;
import tec.bd.weather.entity.State;

import java.util.List;
import java.util.Optional;

public interface CityService {

    List<City> getAllCitys();

    Optional<City> getCityById(int cityId);
    City newCity(String cityName,int zipCode,int stateId);

    void removeByCityId(int cityId);

    City updateCity(City city);
}
