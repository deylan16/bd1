package tec.bd.weather.repository.sql;

import tec.bd.weather.entity.Country;
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

public class StateRepository implements Repository<State, Integer> {

    private final DataSource dataSource;

    public StateRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public Optional<State> findById(Integer stateId) {

        try (Connection connection = this.dataSource.getConnection();
             CallableStatement stmt = connection.prepareCall(Queries.FIND_ALL_STATES_PROC)) {

            stmt.setInt(1, stateId);
            var rs = stmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                var state = new State(rs.getInt(1), rs.getString(2),rs.getInt(3));
                return Optional.of(state);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve state", e);
        }
    }

    @Override
    public List<State> findAll() {
        List<State> allStates = new ArrayList<>();
        try (Connection connection = this.dataSource.getConnection();
             CallableStatement stmt = connection.prepareCall(Queries.FIND_ALL_STATES_PROC)) {

            stmt.setInt("p_state_id", 0);
            var rs = stmt.executeQuery();
            System.out.println(rs);

            // loop through the result set
            while (rs.next()) {
                var state = new State(rs.getInt(1), rs.getString(2),rs.getInt(3));
                allStates.add(state);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve states", e);
        }

        return allStates;
    }

    @Override
    public State save(State state) {
        try (Connection connection = this.dataSource.getConnection();
            CallableStatement stmt = connection.prepareCall(Queries.CREATE_STATE_PROC)) {
                stmt.setString("p_state_name",state.getStateName());
                stmt.setInt("p_country_code",state.getCountry_id());
                stmt.registerOutParameter("p_new_state_id", Types.INTEGER);
                stmt.executeUpdate();
                //stmt.executeQuery();

                var newState = new State(stmt.getInt("p_new_state_id"),state.getStateName(),state.getCountry_id());

                return newState;

        } catch (SQLException e) {
            throw new RuntimeException("failed to save state",e);
        }
    }

    @Override
    public void delete(Integer stateId) {
        try (Connection connection = this.dataSource.getConnection();
             CallableStatement stmt = connection.prepareCall(Queries.DELETE_STATE_BY_ID_PROC)) {

            stmt.setInt(1, stateId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to state country", e);
        }

    }

    @Override
    public State update(State state) {
        // es muy similar al de save
        try (Connection connection = this.dataSource.getConnection();
             CallableStatement stmt = connection.prepareCall(Queries.UPDATE_STATE_PROC)) {

            stmt.setInt("p_state_id", state.getId());
            stmt.setString("p_state_name", state.getStateName());
            stmt.setInt("p_country_code", state.getCountry_id());
            stmt.executeUpdate();

            return state;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update state", e);
        }
    }
}
