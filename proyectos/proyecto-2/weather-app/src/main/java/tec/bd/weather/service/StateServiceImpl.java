package tec.bd.weather.service;

import tec.bd.weather.entity.Country;
import tec.bd.weather.entity.State;
import tec.bd.weather.repository.Repository;

import java.util.List;
import java.util.Optional;

public class StateServiceImpl implements StateService{

    private Repository<State, Integer> stateRepository;

    public StateServiceImpl(Repository<State, Integer> stateRepository) {
        this.stateRepository = stateRepository;
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
        //logica no permitir que contryname sea nulo o vacio
        //VALIDAR si el country name ya existe ne la base de datos
        // ára esto abria que buscar el nombre del pais countryname en la base de datos
        //y ver si existe. si ya existe no se salva
        var stateToBeSave = new State(null,stateName,countryId);
        var newState = (this.stateRepository.save(stateToBeSave));
        return newState;
    }

    @Override
    public void removeByStateId(int stateId){
        // Validaciones. El country Id es mayor que cero?
        // lanza una exception
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
