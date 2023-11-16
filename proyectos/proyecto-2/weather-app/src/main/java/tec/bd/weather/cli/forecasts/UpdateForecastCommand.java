package tec.bd.weather.cli.forecasts;


import picocli.CommandLine;
import tec.bd.weather.ApplicationContext;
import tec.bd.weather.entity.City;
import tec.bd.weather.entity.Forecast;

import java.sql.Date;

@CommandLine.Command(name = "forecast-update", aliases = {"fu"}, description = "update forecast by id")


public class UpdateForecastCommand implements Runnable {

    @CommandLine.Parameters(paramLabel = "<forecast Id>", description = "The forecast Id.", defaultValue = "0")
    private int forecastId;

    @CommandLine.Parameters(paramLabel = "<city if>", description = "The city id")
    private int city_Id;

    @CommandLine.Parameters(paramLabel = "<Temperature>", description = "The temperature")
    private int temperature;

    @CommandLine.Parameters(paramLabel = "<date>", description = "The date")
    private String date;


    @Override
    public void run() {
        System.out.println("forecast-update");

            // System.out.println("country-create");
            //creasr pais
        var appContext = new ApplicationContext();
        var ForecastService = appContext.getForecastService();
        var oldForecast= new Forecast(forecastId,city_Id,temperature, Date.valueOf(date));
        var UpdateForecast = ForecastService.updateForecast(oldForecast);

        System.out.println("Update Forecast: "+ UpdateForecast.getId() + ", " + UpdateForecast.getCity_id()+ ", " + UpdateForecast.getTemperature()+ ", " + UpdateForecast.getDate());


    }
}
