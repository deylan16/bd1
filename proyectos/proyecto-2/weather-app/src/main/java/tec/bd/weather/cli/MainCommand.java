package tec.bd.weather.cli;

import picocli.CommandLine;
import tec.bd.weather.cli.city.CreateCityCommand;
import tec.bd.weather.cli.city.DeleteCityCommand;
import tec.bd.weather.cli.city.ReadCityCommand;
import tec.bd.weather.cli.city.UpdateCityCommand;
import tec.bd.weather.cli.country.CreateCountryCommand;
import tec.bd.weather.cli.country.DeleteCountryCommand;
import tec.bd.weather.cli.country.ReadCountryCommand;
import tec.bd.weather.cli.country.UpdateCountryCommand;
import tec.bd.weather.cli.state.CreateStateCommand;
import tec.bd.weather.cli.state.DeleteStateCommand;
import tec.bd.weather.cli.state.ReadStateCommand;
import tec.bd.weather.cli.state.UpdateStateCommand;

@CommandLine.Command(
        name= "Weather App",
        subcommands = {
                ForecastByCityCommand.class,
                ForecastByZipCodeCommand.class,
                CreateForecastCommand.class,
                UpdateForecastCommand.class,
                RemoveForecastCommand.class,
                CommandLine.HelpCommand.class,
                AllForecastsCommand.class,
                //Country related Commands
                CreateCountryCommand.class,
                DeleteCountryCommand.class,
                ReadCountryCommand.class,
                UpdateCountryCommand.class,
                //State related Commands
                CreateStateCommand.class,
                DeleteStateCommand.class,
                ReadStateCommand.class,
                UpdateStateCommand.class,
                //City related Commands
                CreateCityCommand.class,
                DeleteCityCommand.class,
                ReadCityCommand.class,
                UpdateCityCommand.class
        },description = "Weather App"
)

public class MainCommand implements Runnable{
    @Override
    public void run() {

    }
}
