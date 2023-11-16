package tec.bd.weather.cli.forecasts;

import picocli.CommandLine;
import tec.bd.weather.ApplicationContext;
import tec.bd.weather.entity.Forecast;

import java.sql.Date;
import java.util.Objects;

@CommandLine.Command(name = "forecast-read-zip", aliases = {"frz"}, description = "read forecast by zip code")
public class ReadForecastByZipCommand implements Runnable{

    @CommandLine.Parameters(paramLabel = "<forecast zip>", description = "The forecast zip.", defaultValue = "0")
    private Integer forecastZip;

    @Override
    public void run() {
        System.out.println("Read Forecast. forecast Zip: " + forecastZip);

        var appContext = new ApplicationContext();
        var forecastService = appContext.getForecastService();
        var cityService = appContext.getCityService();
        var stateService = appContext.getStateService();
        var countryService = appContext.getCountryService();

        // Es que no se pasó un country Id como parametro al comando

        if (forecastZip== 0) {
            var forecasts = forecastService.getAllForecasts_Zip();

            System.out.println("Forecasts");
            System.out.println("=============================================");
            for (Forecast c: forecasts) {
                System.out.println(c.getId() + "\t" + c.getCity_id()+ "\t" + c.getTemperature()+ "\t" + c.getDate()+ "\t" + c.getZipCode());
            }
        } else {

            var forecasts = forecastService.getForecastByZip(forecastZip);

            System.out.println("Forecasts");
            System.out.println("=============================================");
            System.out.println("Zip"+ "\t" + "City"+ "\t" + "State"+ "\t"+ "\t"+ "\t" + "Country");
            for (Forecast c: forecasts) {
                var city = cityService.getCityById(c.getCity_id());
                var state = stateService.getStateById(city.get().getState_id());
                var country = countryService.getCountryById(state.get().getCountry_id());
                System.out.println(c.getZipCode()+ "\t" + city.get().getCityName()+ "\t" + state.get().getStateName()+ "\t" + country.get().getCountryName());
                break;
            }
            System.out.println("=============");
            System.out.println("Id" + "\t" +"Temperature"+ "\t" + "Date");

            for (Forecast c: forecasts) {

                System.out.println(c.getId() + "\t" + c.getTemperature()+ "C / "+((c.getTemperature()*1.8)+32)+"F"+ "\t" + c.getDate());
            }
        }
    }
}
