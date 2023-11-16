package tec.bd.weather.repository.service;

import tec.bd.weather.entity.Country;
import tec.bd.weather.entity.ForecastLog;
import tec.bd.weather.repository.Repository;
import tec.bd.weather.repository.sql.ForecastLogRepository;

import java.util.List;

public class ForecastLogServiceImpl implements ForecastLogService{
    private ForecastLogRepository forecastLogRepository;

    public ForecastLogServiceImpl(ForecastLogRepository forecastLogRepository) {
        this.forecastLogRepository = forecastLogRepository;
    }

    @Override
    public List<ForecastLog> findByQuantiry(int quantity) {
        return this.forecastLogRepository.findByQuantiry(quantity);
    }
}
