package tec.bd.weather.repository.sql;

import tec.bd.weather.entity.City;
import tec.bd.weather.entity.Forecast;
import tec.bd.weather.entity.ForecastAnterior;
import tec.bd.weather.repository.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ForecastRepository implements Repository<Forecast, Integer> {

    private final DataSource dataSource;

    public ForecastRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Forecast> findById(Integer forecast_id) {
        try (Connection connection = this.dataSource.getConnection();
             CallableStatement stmt = connection.prepareCall(Queries.FIND_ALL_FORECASTS_PROC)) {

            stmt.setInt(1, forecast_id);
            var rs = stmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                var forecast = new Forecast(rs.getInt(1), rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getDate(5));
                return Optional.of(forecast);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve forecast", e);
        }
    }

    @Override
    public List<Forecast> findAll() {
        List<Forecast> allForecasts = new ArrayList<>();
        try (Connection connection = this.dataSource.getConnection();
             CallableStatement stmt = connection.prepareCall(Queries.FIND_ALL_FORECASTS_PROC)) {

            stmt.setInt("p_forecast_id", 0);
            var rs = stmt.executeQuery();
            System.out.println(rs);

            // loop through the result set
            while (rs.next()) {
                var forecast = new Forecast(rs.getInt(1), rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getDate(5));
                allForecasts.add(forecast);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve Forecast", e);
        }

        return allForecasts;
    }

    @Override
    public Forecast save(Forecast forecast) {
        try (Connection connection = this.dataSource.getConnection();
             CallableStatement stmt = connection.prepareCall(Queries.CREATE_FORECAST_PROC)) {
            stmt.setInt("p_city_code",forecast.getCity_id());
            stmt.setInt("p_temperature",forecast.getTemperature());
            stmt.setDate("p_date",forecast.getDate());

            stmt.registerOutParameter("p_new_forecast_id", Types.INTEGER);
            stmt.executeUpdate();
            //stmt.executeQuery();

            var newForecast = new Forecast(stmt.getInt("p_new_forecast_id"),forecast.getCity_id(),forecast.getTemperature(),forecast.getDate());

            return newForecast;

        } catch (SQLException e) {
            throw new RuntimeException("failed to save city",e);
        }
    }

    @Override
    public void delete(Integer forecast_id) {
        try (Connection connection = this.dataSource.getConnection();
             CallableStatement stmt = connection.prepareCall(Queries.DELETE_FORECAST_BY_ID_PROC)) {

            stmt.setInt(1, forecast_id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete forecast", e);
        }

    }

    @Override
    public Forecast update(Forecast forecast) {
        try (Connection connection = this.dataSource.getConnection();
             CallableStatement stmt = connection.prepareCall(Queries.UPDATE_FORECAST_PROC)) {

            stmt.setInt("p_forecast_id", forecast.getId());
            stmt.setInt("p_city_code",forecast.getCity_id());
            stmt.setInt("p_temperature",forecast.getTemperature());
            stmt.setDate("p_date",forecast.getDate());

            stmt.executeUpdate();

            return forecast;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update forecast", e);
        }
    }


    public List<Forecast>  findByDate(java.sql.Date forecast_date) {


        List<Forecast> allForecasts = new ArrayList<>();
        try (Connection connection = this.dataSource.getConnection();
             CallableStatement stmt = connection.prepareCall(Queries.FIND_ALL_FORECASTS_DATE_PROC)) {

            stmt.setDate("p_date", forecast_date);
            var rs = stmt.executeQuery();
            System.out.println(rs);

            // loop through the result set
            while (rs.next()) {
                var forecast = new Forecast(rs.getInt(1), rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getDate(5));
                allForecasts.add(forecast);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve Forecast", e);
        }

        return allForecasts;
    }

    public List<Forecast> findAll_Date() {
        List<Forecast> allForecasts = new ArrayList<>();
        try (Connection connection = this.dataSource.getConnection();
             CallableStatement stmt = connection.prepareCall(Queries.FIND_ALL_FORECASTS_DATE_PROC)) {

            stmt.setDate("p_date", null);
            var rs = stmt.executeQuery();
            System.out.println(rs);

            // loop through the result set
            while (rs.next()) {
                var forecast = new Forecast(rs.getInt(1), rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getDate(5));
                allForecasts.add(forecast);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve Forecast", e);
        }

        return allForecasts;
    }
    public List<Forecast> findByZip(int forecast_zip) {

        List<Forecast> allForecasts = new ArrayList<>();
        try (Connection connection = this.dataSource.getConnection();
             CallableStatement stmt = connection.prepareCall(Queries.FIND_ALL_FORECASTS_ZIP_PROC)) {

            stmt.setInt("p_zip", forecast_zip);
            var rs = stmt.executeQuery();
            System.out.println(rs);

            // loop through the result set
            while (rs.next()) {
                var forecast = new Forecast(rs.getInt(1), rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getDate(5));
                allForecasts.add(forecast);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve Forecast", e);
        }
        return allForecasts;
    }

    public List<Forecast> findAll_Zip() {
        List<Forecast> allForecasts = new ArrayList<>();
        try (Connection connection = this.dataSource.getConnection();
             CallableStatement stmt = connection.prepareCall(Queries.FIND_ALL_FORECASTS_ZIP_PROC)) {

            stmt.setInt("p_zip", 0);
            var rs = stmt.executeQuery();
            System.out.println(rs);

            // loop through the result set
            while (rs.next()) {
                var forecast = new Forecast(rs.getInt(1), rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getDate(5));
                allForecasts.add(forecast);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve Forecast", e);
        }

        return allForecasts;
    }
}
