package tec.bd.weather.repository.sql;

import tec.bd.weather.entity.City;
import tec.bd.weather.entity.State;
import tec.bd.weather.repository.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CityRepository implements Repository<City, Integer> {

    private final DataSource dataSource;

    public CityRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public Optional<City> findById(Integer cityId) {

        try (Connection connection = this.dataSource.getConnection();
             CallableStatement stmt = connection.prepareCall(Queries.FIND_ALL_CITYS_PROC)) {

            stmt.setInt(1, cityId);
            var rs = stmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                var city = new City(rs.getInt(1), rs.getString(2),rs.getInt(3),rs.getInt(4));
                return Optional.of(city);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve city", e);
        }
    }

    @Override
    public List<City> findAll() {
        List<City> allCitys = new ArrayList<>();
        try (Connection connection = this.dataSource.getConnection();
             CallableStatement stmt = connection.prepareCall(Queries.FIND_ALL_CITYS_PROC)) {

            stmt.setInt("p_city_id", 0);
            var rs = stmt.executeQuery();
            System.out.println(rs);

            // loop through the result set
            while (rs.next()) {
                var city = new City(rs.getInt(1), rs.getString(2),rs.getInt(3),rs.getInt(4));
                allCitys.add(city);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve states", e);
        }

        return allCitys;
    }

    @Override
    public City save(City city) {
        try (Connection connection = this.dataSource.getConnection();
            CallableStatement stmt = connection.prepareCall(Queries.CREATE_CITY_PROC)) {
                stmt.setString("p_city_name",city.getCityName());
                stmt.setInt("p_zip_code",city.getZipCode());
                stmt.setInt("p_state_code",city.getState_id());

                stmt.registerOutParameter("p_new_city_id", Types.INTEGER);
                stmt.executeUpdate();
                //stmt.executeQuery();

                var newCity = new City(stmt.getInt("p_new_city_id"),city.getCityName(),city.getZipCode(),city.getState_id());

                return newCity;

        } catch (SQLException e) {
            throw new RuntimeException("failed to save city",e);
        }
    }

    @Override
    public void delete(Integer cityId) {
        try (Connection connection = this.dataSource.getConnection();
             CallableStatement stmt = connection.prepareCall(Queries.DELETE_CITY_BY_ID_PROC)) {

            stmt.setInt(1, cityId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete city", e);
        }

    }

    @Override
    public City update(City city) {
        // es muy similar al de save
        try (Connection connection = this.dataSource.getConnection();
             CallableStatement stmt = connection.prepareCall(Queries.UPDATE_CITY_PROC)) {

            stmt.setInt("p_city_id", city.getId());
            stmt.setString("p_city_name", city.getCityName());
            stmt.setInt("p_zip_code",city.getZipCode());
            stmt.setInt("p_state_code", city.getState_id());
            stmt.executeUpdate();

            return city;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update city", e);
        }
    }
}
