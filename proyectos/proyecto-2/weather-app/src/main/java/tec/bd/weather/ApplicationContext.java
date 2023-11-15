package tec.bd.weather;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.sqlite.SQLiteDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import tec.bd.weather.entity.City;
import tec.bd.weather.entity.Country;
import tec.bd.weather.entity.ForecastAnterior;
import tec.bd.weather.entity.State;
import tec.bd.weather.repository.memory.InMemoryForecastRepository;
import tec.bd.weather.repository.Repository;
import tec.bd.weather.repository.sql.CityRepository;
import tec.bd.weather.repository.sql.CountryRepository;
import tec.bd.weather.repository.sql.ForecastAnteriorRepository;
import tec.bd.weather.repository.sql.StateRepository;
import tec.bd.weather.service.*;


import javax.sql.DataSource;

public class ApplicationContext {

    private static final String SQLITE_DB_URL = "jdbc:sqlite::resource:sqlite/weather-service.db";

    private static final String MYSQL_DB_URL = "jdbc:mysql://localhost:3306/weather_service" +
            "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String MYSQL_DB_USERNAME = "root";
    private static final String MYSQL_DB_PASSWORD = "De16072002-";

    private DataSource mysqlDataSource;



    private DataSource sqliteDataSource;

    private Repository<ForecastAnterior, Integer> inMemoryForecastRepository;
    private Repository<ForecastAnterior, Integer> sqlForecastRepository;

    private Repository<Country, Integer> sqlCountryRepository;

    private Repository<State, Integer> sqlStateRepository;
    private Repository<City, Integer> sqlCityRepository;

    private WeatherService weatherService;

    private CountryService countryService;

    private StateService stateService;

    private CityService CityService;

    public ApplicationContext() {
        initSqliteDataSource();
        initMySqlDataSource();


        initInMemoryForecastRepository();
        initSQLForecastRepository();
        initSQLCountryRepository();
        initSQLStateRepository();
        initSQLCityRepository();

        initWeatherService();

        initStateService();
        initCountryService();
        initCityService();



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

    private void initInMemoryForecastRepository() {
        this.inMemoryForecastRepository = new InMemoryForecastRepository();
    }

    private void initSQLForecastRepository() {
        this.sqlForecastRepository = new ForecastAnteriorRepository(this.sqliteDataSource);
    }

    private void initWeatherService() {
        this.weatherService = new WeatherServiceImpl(this.sqlForecastRepository);
    }

    public Repository<ForecastAnterior, Integer> getInMemoryForecastRepository() {
        return this.inMemoryForecastRepository;
    }

    public Repository<ForecastAnterior, Integer> getSqlForecastRepository() {
        return this.sqlForecastRepository;
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
        this.CityService = new CityServiceImpl(this.sqlCityRepository);
    }

    public CityService getCityService(){
        return this.CityService;
    }

}
