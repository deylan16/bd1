package tec.bd.weather.repository.sql;

import tec.bd.weather.entity.ForecastAnterior;
import tec.bd.weather.repository.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class ForecastAnteriorRepository implements Repository<ForecastAnterior, Integer>  {

    private final DataSource dataSource;

    public ForecastAnteriorRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<ForecastAnterior> findById(Integer forecastID) {

        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(Queries.FIND_FORECAST_BY_ID)) {
             stmt.setString(1, String.valueOf(forecastID));
             var rs =stmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                var forecast = this.fromResultSet(rs);
                return Optional.of(forecast);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve forecast", e);
        }
        return Optional.empty();

    }

    @Override
    public List<ForecastAnterior> findAll() {

        // JDBC URL for SQLite database in src/main/resources/sqlite

        // 1. Cadena de conexion
        // String url = "jdbc:sqlite::resource:sqlite/weather-service.db";

        List<ForecastAnterior> allForecastAnteriors = new ArrayList<>();
        try (Connection connection = this.dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(Queries.FIND_ALL_FORECAST)) {

            // loop through the result set
            while (rs.next()) {
                var forecast = this.fromResultSet(rs);
                allForecastAnteriors.add(forecast);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve forecasts", e);
        }

        return allForecastAnteriors;
    }

    @Override
    public ForecastAnterior save(ForecastAnterior forecastAnterior) {
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(Queries.INSERT_NEW_FORECAST, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, forecastAnterior.getCountryName());
            stmt.setString(2, forecastAnterior.getCityName());
            stmt.setString(3, forecastAnterior.getZipCode());
            stmt.setDate(4, new java.sql.Date(forecastAnterior.getForecastDate().getTime()));
            stmt.setFloat(5, forecastAnterior.getTemperature());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                // Get the last inserted ID
                forecastAnterior.setId(generatedKeys.getInt(1));
            }
            return forecastAnterior;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save forecast", e);
        }
    }

    @Override
    public void delete(Integer forecastiId) {
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(Queries.DELETE_FORECAST_BY_ID)) {

            stmt.setInt(1, forecastiId);
            stmt.executeUpdate();


            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                // Get the last inserted ID
                //forecast.setId(generatedKeys.getInt(1));
            }
            //return forecast;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete forecast", e);
        }
    }

    @Override
    public ForecastAnterior update(ForecastAnterior source) {
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(Queries.UPDATE_FORECAST)) {

            stmt.setString(1, source.getCountryName());
            stmt.setString(2, source.getCityName());
            stmt.setString(3, source.getZipCode());
            stmt.setDate(4, new java.sql.Date(source.getForecastDate().getTime()));
            stmt.setFloat(5, source.getTemperature());
            stmt.setInt(6, source.getId());
            int affectedRows = stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                // Get the last inserted ID
                //source.setId(generatedKeys.getInt(1));
            }
            return source;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save forecast", e);
        }
    }

    private ForecastAnterior fromResultSet(ResultSet rs) throws SQLException {
        var forecastDate = rs.getDate("forecast_date");
        return new ForecastAnterior(
                rs.getInt("forecast_id"),
                rs.getString("country_name"),
                rs.getString("city_name"),
                rs.getString("zip_code"),
                new Date(forecastDate.getTime()),
                rs.getFloat("temperature"));
    }

}
