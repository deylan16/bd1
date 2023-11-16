package tec.bd.weather.repository.sql;

import tec.bd.weather.entity.Country;
import tec.bd.weather.entity.Forecast;
import tec.bd.weather.entity.ForecastLog;
import tec.bd.weather.repository.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ForecastLogRepository{

    private final DataSource dataSource;

    public ForecastLogRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public List<ForecastLog>  findByQuantiry(Integer quantity) {


        List<ForecastLog> allForecastLog = new ArrayList<>();
        try (Connection connection = this.dataSource.getConnection();
             CallableStatement stmt = connection.prepareCall(Queries.FIND_ALL_FORECASTS_LOG_PROC)) {

            stmt.setInt("p_cantidad", quantity);
            var rs = stmt.executeQuery();
            System.out.println(rs);

            // loop through the result set
            while (rs.next()) {
                var ForecastLog = new ForecastLog(rs.getInt(1), rs.getDate(2),rs.getString(3),rs.getInt(4));
                allForecastLog.add(ForecastLog);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve ForecastLog", e);
        }

        return allForecastLog;
    }


}
