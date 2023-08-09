package tec.bd.weather;

public interface WeatherService {
    float getByCityTemperature(String city);

    float getByZipCodeTemperature(String zipCode);
}
