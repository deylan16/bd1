package tec.bd.weather.cli.ForecastLog;

import picocli.CommandLine;
import tec.bd.weather.ApplicationContext;
import tec.bd.weather.entity.Forecast;
import tec.bd.weather.entity.ForecastLog;

@CommandLine.Command(name = "Log", aliases = {"log"}, description = "read forecast log")
public class LogCommand implements Runnable{

    @CommandLine.Parameters(paramLabel = "<Quantity>", description = "The quantity.", defaultValue = "0")
    private int quantity;

    @Override
    public void run() {
        System.out.println("Read Forecast log . forecast log quantity: " + quantity);

        var appContext = new ApplicationContext();
        var forecastLogService = appContext.getForecastLogService();


        // Es que no se pasó un country Id como parametro al comando

        var forecastsLog = forecastLogService.findByQuantiry(quantity);

        System.out.println("Forecasts log");
        System.out.println("=============================================");
        System.out.println("Id"+ "\t" + "Last_modified_on"+ "\t" + "Forecast_id"+ "\t"+ "\t"  + "Entry_text");
        for (ForecastLog c: forecastsLog) {
            System.out.println(c.getId() + "\t" + c.getLast_modified_on()+ "\t"+ "\t" + "\t" + "\t"  + c.getForecast_code()+ "\t"+ "\t" + "\t"  + c.getEntry_text());
        }

    }
}
