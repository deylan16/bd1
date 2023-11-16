package tec.bd.weather.entity;

public class City {
    private Integer id;

    private String cityName;

    private Integer zipCode;

    private Integer state_id;

    public City(Integer id, String cityName, Integer zipCode, Integer state_id) {
        this.id = id;
        this.cityName = cityName;
        this.zipCode = zipCode;
        this.state_id = state_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getState_id() {
        return state_id;
    }

    public void setState_id(Integer state_id) {
        this.state_id = state_id;
    }
}
