package tec.bd.weather.repository.service;

import tec.bd.weather.entity.Country;
import tec.bd.weather.entity.ForecastLog;
import tec.bd.weather.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface ForecastLogService {



    List<ForecastLog>  findByQuantiry(int quantity);
}

