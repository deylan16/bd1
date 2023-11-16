package tec.bd.weather.cli.forecasts;


import picocli.CommandLine;
import tec.bd.weather.ApplicationContext;

import java.sql.Date;

@CommandLine.Command(name = "forecast-create", aliases = {"fc"}, description = "Create new forecast for country")
public class CreateForecastCommand implements Runnable{

    @CommandLine.Parameters(paramLabel = "<city id>", description = "The city id")
    private int city_id;

    @CommandLine.Parameters(paramLabel = "<Temperature>", description = "The temperature")
    private int temperature;

    @CommandLine.Parameters(paramLabel = "<date>", description = "The date")
    private String date;


    @Override
    public void run() {
       // System.out.println("country-create");
        //creasr pais
        var appContext = new ApplicationContext();
        var forecastService = appContext.getForecastService();
        var newForecast =forecastService.newForecast(city_id,temperature, Date.valueOf(date));

        System.out.println("New Forecast: "+ newForecast.getId() + ", " + newForecast.getCity_id()+ ", " + newForecast.getTemperature()+ ", " + newForecast.getDate());
    }
}
