package tec.bd.weather;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class WeatherServiceImplTest {

    @Test
    public void GivenACity_WhenValidCity_ThenReturnTemperature(){
        //Arrange
        var weatherService = new WeatherServiceImpl();
        // Act
        var actual = weatherService.getTemperature("Alajuela");

        // Assert
        assertThat( actual ).isEqualTo(22.0f);
    }
}
