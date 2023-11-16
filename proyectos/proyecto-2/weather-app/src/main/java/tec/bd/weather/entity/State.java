package tec.bd.weather.entity;

public class State {

    private Integer id;

    private String stateName;

    private Integer country_id;

    public State(Integer id, String stateName, Integer country_id) {
        this.id = id;
        this.stateName = stateName;
        this.country_id = country_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Integer getCountry_id() {
        return country_id;
    }

    public void setCountry_id(Integer country_id) {
        this.country_id = country_id;
    }
}
