package tec.bd.weather.service;

import tec.bd.weather.entity.Country;
import tec.bd.weather.entity.Forecast;
import tec.bd.weather.repository.Repository;

import java.util.List;
import java.util.Optional;

public class CountryServiceImpl implements CountryService{

    private Repository<Country, Integer> countryRepository;

    public CountryServiceImpl(Repository<Country, Integer> countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> getAllCountries() {
        return this.countryRepository.findAll();

    }

    @Override
    public Optional<Country> getCountryById(int countryId) {
        return this.countryRepository.findById(countryId);
    }

    @Override
    public Country newCountry(String countryName) {
        //logica no permitir que contryname sea nulo o vacio
        //VALIDAR si el country name ya existe ne la base de datos
        // ára esto abria que buscar el nombre del pais countryname en la base de datos
        //y ver si existe. si ya existe no se salva
        var countryToBeSave = new Country(null,countryName);
        var newCountry = (this.countryRepository.save(countryToBeSave));
        return newCountry;
    }

    @Override
    public void removeByCountryId(int countryId){
        // Validaciones. El country Id es mayor que cero?
        // lanza una exception
        if (countryId <= 0) {
            throw new RuntimeException("Country Id requeride < 0");
        }
        var countryFromDBOpt = this.getCountryById(countryId);

        if (countryFromDBOpt.isEmpty()) {
            throw new RuntimeException("Country Id: " + countryId + " not found");
        }

        this.countryRepository.delete(countryId);
    }

    @Override
    public Country updateCountry(Country country) {
        // TODO: validar si el country.Id existe en la base de datos
        // TODO: validar si el nombre del country ya existe en la BD

        var countryUpdated = this.countryRepository.update(country);
        return countryUpdated;
    }
}
