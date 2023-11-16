package tec.bd.weather.entity;

import java.sql.Date;

public class ForecastLog {


    private Integer id;

    private java.sql.Date last_modified_on;

    private String entry_text;

    private Integer forecast_code;

    public ForecastLog(Integer id, Date last_modified_on, String entry_text, Integer forecast_code) {
        this.id = id;
        this.last_modified_on = last_modified_on;
        this.entry_text = entry_text;
        this.forecast_code = forecast_code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLast_modified_on() {
        return last_modified_on;
    }

    public void setLast_modified_on(Date last_modified_on) {
        this.last_modified_on = last_modified_on;
    }

    public String getEntry_text() {
        return entry_text;
    }

    public void setEntry_text(String entry_text) {
        this.entry_text = entry_text;
    }

    public Integer getForecast_code() {
        return forecast_code;
    }

    public void setForecast_code(Integer forecast_code) {
        this.forecast_code = forecast_code;
    }
}
