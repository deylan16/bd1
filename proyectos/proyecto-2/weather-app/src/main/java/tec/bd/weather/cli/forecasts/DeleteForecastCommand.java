package tec.bd.weather.cli.forecasts;


import picocli.CommandLine;
import tec.bd.weather.ApplicationContext;

@CommandLine.Command(name = "forecast-Delete", aliases = {"fd"}, description = "forecast city by id")
public class DeleteForecastCommand implements Runnable{
    @CommandLine.Parameters(paramLabel = "<forecast Id>", description = "The forecast Id.", defaultValue = "0")
    private int forecastId;

    @Override
    public void run() {

        var appContext = new ApplicationContext();
        var forecastService = appContext.getForecastService();

        try {
            forecastService.removeByForecastId(forecastId);
            System.out.println("Forecast Id :" + forecastId + " deleted");
        } catch (Exception e) {
            System.err.println("Forecast Id :" + forecastId + " not deleted. Reason: " + e.getMessage());
        }

    }
}
