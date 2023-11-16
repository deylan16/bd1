package tec.bd.weather.entity;

import java.util.Date;

public class ForecastAnterior {
    private Integer id;
    private float temperature;

    private Date forecastDate;
    private String cityName;

    private String zipCode;
    private String countryName;

    public ForecastAnterior() { }
    public ForecastAnterior(Integer id, String countryName, String cityName, String zipCode, Date forecastDate, float temperature) {
        this.id = id;
        this.temperature = temperature;
        this.cityName = cityName;
        this.zipCode = zipCode;
        this.forecastDate = forecastDate;
        this.countryName = countryName;

    }
    public ForecastAnterior(String countryName, String cityName, String zipCode, Date forecastDate, float temperature) {
        this(0, countryName, cityName, zipCode, forecastDate, temperature);
    }



    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public Date getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(Date forecastDate) {
        this.forecastDate = forecastDate;
    }

    @Override
    public String toString() {
        return "Forecast {" +
                "id = " + id +
                ", temperature = " + temperature +
                ", country = '" + countryName + '\'' +
                ", city = '" + cityName + '\'' +
                ", zipCode = '" + zipCode + '\'' +
                ", date = '" + forecastDate + '\'' +
                ", temperature = " + temperature +
                '}';
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public static void validate(ForecastAnterior forecastAnterior) {
        if (forecastAnterior == null) {
            throw new RuntimeException("No weather forecast was provided");
        }
        //if (forecast.getId() == null) {
        //    throw new RuntimeException("No weather forecast ID was provided");
       // }
        //if (forecast.getId() < 1) {
        //    throw new RuntimeException("Weather forecast ID invalid");
        //}
        if (forecastAnterior.getCountryName().isBlank()) {
            throw new RuntimeException("Weather forecast country invalid");
        }
        if (forecastAnterior.getCityName().isBlank()) {
            throw new RuntimeException("Weather forecast city invalid");
        }
        if (forecastAnterior.getZipCode().isBlank()) { // se podria utilizar un Regex
            throw new RuntimeException("Weather forecast zip code invalid");
        }
        if (forecastAnterior.getTemperature() < 0) {
            throw new RuntimeException("Weather forecast temperature invalid");
        }
    }


}
