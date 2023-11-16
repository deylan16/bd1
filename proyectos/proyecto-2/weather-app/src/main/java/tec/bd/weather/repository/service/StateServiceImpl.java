package tec.bd.weather.repository.service;

import tec.bd.weather.entity.City;
import tec.bd.weather.entity.Country;
import tec.bd.weather.entity.State;
import tec.bd.weather.repository.Repository;

import java.util.List;
import java.util.Optional;

public class StateServiceImpl implements StateService{

    private Repository<State, Integer> stateRepository;

    private CountryService countryRepository;
    private CityService cityRepository;

    public StateServiceImpl(Repository<State, Integer> stateRepository) {
        this.stateRepository = stateRepository;

    }
    public void initService(CountryService countryRepository,CityService cityRepository){
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;

    }

    @Override
    public List<State> getAllStates() {
        return this.stateRepository.findAll();

    }

    @Override
    public Optional<State> getStateById(int stateId) {
        return this.stateRepository.findById(stateId);
    }

    @Override
    public State newState(String stateName,int countryId) {
        for (Country country : this.countryRepository.getAllCountries()) {

            if (country.getId() != countryId){
                throw new RuntimeException("countryId id not exist");
            }

        }
        var stateToBeSave = new State(null,stateName,countryId);
        var newState = (this.stateRepository.save(stateToBeSave));
        return newState;
    }

    @Override
    public void removeByStateId(int stateId){
        for (City city : this.cityRepository.getAllCitys()) {
            if (city.getState_id() == stateId){
                throw new RuntimeException("have city");
            }

        }
        if (stateId <= 0) {
            throw new RuntimeException("State Id requeride < 0");
        }
        var stateFromDBOpt = this.getStateById(stateId);

        if (stateFromDBOpt.isEmpty()) {
            throw new RuntimeException("State Id: " + stateId + " not found");
        }

        this.stateRepository.delete(stateId);
    }

    @Override
    public State updateState(State state) {
        // TODO: validar si el state.Id existe en la base de datos
        // TODO: validar si el nombre del state ya existe en la BD
        // TODO: validar que exista el state.country_id

        var stateUpdated = this.stateRepository.update(state);
        return stateUpdated;
    }
}
