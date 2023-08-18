package tec.bd.weather.cli;

import picocli.CommandLine;

@CommandLine.Command(
        name= "Weather App",
        subcommands = {
                WeatherByCityCommand.class,
                WeatherByZipCodeCommand.class,
                CommandLine.HelpCommand.class
        },description = "Weather App Service by City and city code"
)

public class MainCommand implements Runnable{
    @Override
    public void run() {

    }
}
