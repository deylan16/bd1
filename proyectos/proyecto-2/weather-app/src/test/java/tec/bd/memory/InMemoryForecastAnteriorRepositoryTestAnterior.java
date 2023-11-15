package tec.bd.memory;

import org.junit.jupiter.api.Test;
import tec.bd.weather.repository.memory.InMemoryForecastAnteriorRepository;

import static org.assertj.core.api.Assertions.*;

public class InMemoryForecastAnteriorRepositoryTestAnterior {

    private InMemoryForecastAnteriorRepository repository;

    @Test
    public void givenInMemoryCollection_whenGetCurrentMaxId_thenReturnMaxId() {
        // Arrange
        this.repository = new InMemoryForecastAnteriorRepository();

        // Act
        var actual = this.repository.getCurrentMaxId();

        // Assert
        assertThat(actual).isEqualTo(4);
    }
}
