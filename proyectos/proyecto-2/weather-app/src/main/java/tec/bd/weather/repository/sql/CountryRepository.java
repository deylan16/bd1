package tec.bd.weather.repository.sql;

import tec.bd.weather.entity.Country;
import tec.bd.weather.repository.Repository;
import tec.bd.weather.repository.sql.Queries;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CountryRepository implements Repository<Country, Integer> {

    private final DataSource dataSource;

    public CountryRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public Optional<Country> findById(Integer countryId) {

        try (Connection connection = this.dataSource.getConnection();
             CallableStatement stmt = connection.prepareCall(Queries.FIND_ALL_COUNTRIES_PROC)) {

            stmt.setInt(1, countryId);
            var rs = stmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                var country = new Country(rs.getInt(1), rs.getString(2));
                return Optional.of(country);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve countries", e);
        }
    }

    @Override
    public List<Country> findAll() {
        List<Country> allCountries = new ArrayList<>();
        try (Connection connection = this.dataSource.getConnection();
             CallableStatement stmt = connection.prepareCall(Queries.FIND_ALL_COUNTRIES_PROC)) {

            stmt.setInt("p_country_id", 0);
            var rs = stmt.executeQuery();
            System.out.println(rs);

            // loop through the result set
            while (rs.next()) {
                var country = new Country(rs.getInt(1), rs.getString(2));
                allCountries.add(country);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve countries", e);
        }

        return allCountries;
    }

    @Override
    public Country save(Country country) {
        try (Connection connection = this.dataSource.getConnection();
            CallableStatement stmt = connection.prepareCall(Queries.CREATE_COUNTRY_PROC)) {
                stmt.setString("p_country_name",country.getCountryName());
                stmt.registerOutParameter("p_new_country_id", Types.INTEGER);
                stmt.executeUpdate();
                //stmt.executeQuery();

                var newCountry = new Country(stmt.getInt("p_new_country_id"),country.getCountryName());

                return newCountry;

        } catch (SQLException e) {
            throw new RuntimeException("failed to save country",e);
        }
    }

    @Override
    public void delete(Integer countryId) {
        try (Connection connection = this.dataSource.getConnection();
             CallableStatement stmt = connection.prepareCall(Queries.DELETE_COUNTRY_BY_ID_PROC)) {

            stmt.setInt(1, countryId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete country", e);
        }

    }

    @Override
    public Country update(Country country) {
        // es muy similar al de save
        try (Connection connection = this.dataSource.getConnection();
             CallableStatement stmt = connection.prepareCall(Queries.UPDATE_COUNTRY_PROC)) {

            stmt.setInt("p_country_id", country.getId());
            stmt.setString("p_country_name", country.getCountryName());
            stmt.executeUpdate();

            return country;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update country", e);
        }
    }
}
