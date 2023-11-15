package tec.bd.weather.repository.sql;

public class Queries {

    public static final String FIND_ALL_FORECAST = "SELECT " +
            "forecast_id, country_name, city_name, zip_code, forecast_date, temperature " +
            "FROM forecast";
    public static final String INSERT_NEW_FORECAST = "INSERT INTO " +
            "forecast(country_name, city_name, zip_code, forecast_date, temperature) " +
            "VALUES(?, ?, ?, ?, ?)";

    public static final String DELETE_FORECAST_BY_ID = "DELETE FROM forecast WHERE forecast_id = ?";


    public static final String FIND_FORECAST_BY_ID = "SELECT " +
            "forecast_id, country_name, city_name, zip_code, forecast_date, temperature " +
            "FROM forecast WHERE forecast_id = ?" ;
    public static final String UPDATE_FORECAST = "UPDATE forecast SET "+
            "country_name = ?, city_name = ?, zip_code = ?, forecast_date = ?, temperature = ? "
            +"WHERE forecast_id = ?";

    //country conections
    public static final String CREATE_COUNTRY_PROC= "{call create_country(?,?)} ";

    public static final String FIND_ALL_COUNTRIES_PROC= "{call find_all_countries(?)}";

    public static final String DELETE_COUNTRY_BY_ID_PROC = "call delete_country(?)";

    public static final String UPDATE_COUNTRY_PROC = "call update_country(?, ?)";


    //state conections
    public static final String CREATE_STATE_PROC= "{call create_state(?,?,?)} ";

    public static final String FIND_ALL_STATES_PROC= "{call find_all_states(?)}";

    public static final String DELETE_STATE_BY_ID_PROC = "call delete_state(?)";

    public static final String UPDATE_STATE_PROC = "call update_state(?, ?,?)";

    //city conections
    public static final String CREATE_CITY_PROC= "{call create_city(?,?,?,?)} ";

    public static final String FIND_ALL_CITYS_PROC= "{call find_all_citys(?)}";

    public static final String DELETE_CITY_BY_ID_PROC = "call delete_city(?)";

    public static final String UPDATE_CITY_PROC = "call update_city(?, ?,?,?)";


    //forecast conections
    public static final String CREATE_FORECAST_PROC= "{call create_forecast(?,?,?,?)} ";

    public static final String FIND_ALL_FORECASTS_PROC= "{call find_all_forecasts(?)}";
    public static final String FIND_ALL_FORECASTS_DATE_PROC= "{call find_all_forecasts_date(?)}";
    public static final String FIND_ALL_FORECASTS_ZIP_PROC= "{call find_all_forecasts_zip(?)}";

    public static final String DELETE_FORECAST_BY_ID_PROC = "call delete_forecast(?)";

    public static final String UPDATE_FORECAST_PROC = "call update_forecast(?, ?,?,?)";





}