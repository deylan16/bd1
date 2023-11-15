package tec.bd.weather.cli.anteriorForecasts;
import picocli.CommandLine;
import tec.bd.weather.ApplicationContext;

@CommandLine.Command(name = "remove-forecast", aliases = { "rf" }, description = "Remove existing forecast data")
public class DeleteForecastCommand implements Runnable {

    @CommandLine.Parameters(paramLabel = "<forecast id>", description = "The forecast id")
    private int removedForecastId;


    @Override
    public void run() {
        try {
            var appContext = new ApplicationContext();
            var weatherService = appContext.getWeatherService();

            weatherService.removeForecast(removedForecastId);
            System.out.println("The Forecast has been removed with id "+ removedForecastId);
        } catch (Exception e) {
            System.err.println("Can't removed forecast. " +  e.getMessage());
        }
    }

}
