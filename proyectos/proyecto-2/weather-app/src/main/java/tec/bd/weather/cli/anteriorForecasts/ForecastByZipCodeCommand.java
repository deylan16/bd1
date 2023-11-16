package tec.bd.weather.cli.anteriorForecasts;

import picocli.CommandLine;
import tec.bd.weather.ApplicationContext;

@CommandLine.Command(name = "by-zip", aliases = { "bz" },description = "Get weather for a Zip Code ")
public class ForecastByZipCodeCommand implements Runnable {

    @CommandLine.Parameters(paramLabel = "<zip code>",description = "The zip code")
    private String zipCode;


    @Override
    public void run() {
        System.out.println("By Zip Code:" + zipCode);

        try {

            var appContext = new ApplicationContext();
            var weatherService = appContext.getWeatherService();
            System.out.println(weatherService.getByZipCodeTemperature(zipCode));
        }catch (Exception e){
            System.err.println(zipCode + " id not soported");
        }
    }
}
