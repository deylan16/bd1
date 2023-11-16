package tec.bd.weather;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.sqlite.SQLiteDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import tec.bd.weather.entity.*;
import tec.bd.weather.repository.memory.InMemoryForecastAnteriorRepository;
import tec.bd.weather.repository.Repository;
import tec.bd.weather.repository.service.*;
import tec.bd.weather.repository.sql.*;



import javax.sql.DataSource;

public class ApplicationContext {

    private static final String SQLITE_DB_URL = "jdbc:sqlite::resource:sqlite/weather-service.db";

    private static final String MYSQL_DB_URL = "jdbc:mysql://localhost:3306/weather_service" +
            "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String MYSQL_DB_USERNAME = "weatherappuser";
    private static final String MYSQL_DB_PASSWORD = "weatherapppass";

    private DataSource mysqlDataSource;



    private DataSource sqliteDataSource;

    private Repository<ForecastAnterior, Integer> inMemoryForecastAnteriorRepository;
    private Repository<ForecastAnterior, Integer> sqlForecastAnteriorRepository;

    private Repository<Country, Integer> sqlCountryRepository;

    private Repository<State, Integer> sqlStateRepository;
    private Repository<City, Integer> sqlCityRepository;
    private ForecastRepository sqlForecastRepository;

    private ForecastLogRepository sqlForecastLogRepository;

    private WeatherService weatherService;

    private CountryService countryService;

    private StateService stateService;

    private CityService cityService;
    private ForecastService forecastService;

    private ForecastLogService forecastLogService;

    public ApplicationContext() {
        initSqliteDataSource();
        initMySqlDataSource();


        initInMemoryForecastAnteriorRepository();
        initSQLForecastAnteriorRepository();
        initSQLCountryRepository();
        initSQLStateRepository();
        initSQLCityRepository();
        initSQLForecastRepository();
        initSQLForecastLogRepository();

        initWeatherService();
        initForecastLogService();
        initForecastService();
        initCityService();
        initStateService();
        initCountryService();

        initTodo();





    }

    private void initTodo() {
        this.cityService.initService(this.stateService,this.forecastService);  //city
        this.countryService.initService(this.stateService);//country
        this.stateService.initService(this.countryService,this.cityService);//state
        this.forecastService.initService(this.cityService); //forecast
    }

    private void initSqliteDataSource() {
        var sqliteDS = new SQLiteDataSource();
        sqliteDS.setUrl(SQLITE_DB_URL);
        this.sqliteDataSource = sqliteDS;
    }

    public void initMySqlDataSource() {
        var mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(MYSQL_DB_URL);
        mysqlDataSource.setUser(MYSQL_DB_USERNAME);
        mysqlDataSource.setPassword(MYSQL_DB_PASSWORD);
        this.mysqlDataSource = mysqlDataSource;
    }

    private void initHikariDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(MYSQL_DB_URL);
        hikariConfig.setUsername(MYSQL_DB_USERNAME);
        hikariConfig.setPassword(MYSQL_DB_PASSWORD);
        hikariConfig.addDataSourceProperty("connectionTestQuery", "SELECT 1");
        hikariConfig.addDataSourceProperty("maximumPoolSize", "4");
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        this.mysqlDataSource = new HikariDataSource(hikariConfig);
    }

    private void initInMemoryForecastAnteriorRepository() {
        this.inMemoryForecastAnteriorRepository = new InMemoryForecastAnteriorRepository();
    }

    private void initSQLForecastAnteriorRepository() {
        this.sqlForecastAnteriorRepository = new ForecastAnteriorRepository(this.sqliteDataSource);
    }

    private void initWeatherService() {
        this.weatherService = new WeatherServiceImpl(this.sqlForecastAnteriorRepository);
    }

    public Repository<ForecastAnterior, Integer> getInMemoryForecastAnteriorRepository() {
        return this.inMemoryForecastAnteriorRepository;
    }

    public Repository<ForecastAnterior, Integer> getSqlForecastAnteriorRepository() {
        return this.sqlForecastAnteriorRepository;
    }

    public WeatherService getWeatherService() {
        return this.weatherService;
    }

    //State Service
    private void initSQLStateRepository() {
        this.sqlStateRepository = new StateRepository(this.mysqlDataSource);
    }

    public Repository<State, Integer> getSqlStateRepository() {
        return this.sqlStateRepository;
    }

    private void initStateService() {
        this.stateService = new StateServiceImpl(this.sqlStateRepository);
    }

    public StateService getStateService(){
        return this.stateService;
    }

    //Country Service

    private void initSQLCountryRepository() {
        this.sqlCountryRepository = new CountryRepository(this.mysqlDataSource);
    }

    public Repository<Country, Integer> getSqlCountryRepository() {
        return this.sqlCountryRepository;
    }

    private void initCountryService() {
        this.countryService = new CountryServiceImpl(this.sqlCountryRepository);
    }

    public CountryService getCountryService(){
        return this.countryService;
    }

    //City Service

    private void initSQLCityRepository() {
        this.sqlCityRepository = new CityRepository(this.mysqlDataSource);
    }

    public Repository<City, Integer> getSqlCityRepository() {
        return this.sqlCityRepository;
    }

    private void initCityService() {
        this.cityService = new CityServiceImpl(this.sqlCityRepository);
    }

    public CityService getCityService(){
        return this.cityService;
    }

    //Forecast Service

    private void initSQLForecastRepository() {
        this.sqlForecastRepository = new ForecastRepository(this.mysqlDataSource);
    }

    public Repository<Forecast, Integer> getSqlForecastRepository() {
        return this.sqlForecastRepository;
    }

    private void initForecastService() {
        this.forecastService = new ForecastServiceImpl(this.sqlForecastRepository);
    }

    public ForecastService getForecastService(){
        return this.forecastService;
    }

    //ForecastLog Service

    private void initSQLForecastLogRepository() {
        this.sqlForecastLogRepository = new ForecastLogRepository(this.mysqlDataSource);
    }

    public ForecastLogRepository getSqlForecastLogRepository() {
        return this.sqlForecastLogRepository;
    }

    private void initForecastLogService() {
        this.forecastLogService = new ForecastLogServiceImpl(this.sqlForecastLogRepository);
    }

    public ForecastLogService getForecastLogService(){
        return this.forecastLogService;
    }

}
