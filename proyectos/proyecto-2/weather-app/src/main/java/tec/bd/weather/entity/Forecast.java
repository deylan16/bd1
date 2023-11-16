package tec.bd.weather.entity;

import java.sql.Date;

public class Forecast {

    private Integer id;

    private int city_id;

    private int zipCode;

    private Integer temperature;

    private java.sql.Date date;

    public Forecast(Integer id, int city_id, int zipCode, Integer temperature, Date date) {
        this.id = id;
        this.city_id = city_id;
        this.zipCode = zipCode;
        this.temperature = temperature;
        this.date = date;
    }

    public Forecast(Integer id, int city_id, int temperature, java.sql.Date date) {

        this.id = id;
        this.city_id = city_id;
        this.temperature = temperature;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }
}
