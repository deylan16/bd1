package tec.bd.weather.cli.forecasts;

import picocli.CommandLine;
import tec.bd.weather.ApplicationContext;
import tec.bd.weather.entity.City;
import tec.bd.weather.entity.Forecast;

import java.sql.Date;
import java.util.Objects;

@CommandLine.Command(name = "forecast-read-date", aliases = {"frd"}, description = "read forecast by date")
public class ReadForecastByDateCommand implements Runnable{

    @CommandLine.Parameters(paramLabel = "<forecast date>", description = "The forecast Id.", defaultValue = "0")
    private String forecastDate;

    @Override
    public void run() {
        System.out.println("Read Forecast. forecast date: " + forecastDate);

        var appContext = new ApplicationContext();
        var forecastService = appContext.getForecastService();
        var cityService = appContext.getCityService();
        var stateService = appContext.getStateService();
        var countryService = appContext.getCountryService();

        // Es que no se pasó un country Id como parametro al comando

        if (Objects.equals(forecastDate, String.valueOf('0'))) {
            var forecasts = forecastService.getAllForecasts_Date();

            System.out.println("Forecasts");
            System.out.println("=============================================");
            for (Forecast c: forecasts) {
                System.out.println(c.getId() + "\t" + c.getCity_id()+ "\t" + c.getTemperature()+ "\t" + c.getDate()+ "\t" + c.getZipCode());
            }
        } else {


            var forecasts = forecastService.getForecastByDate(Date.valueOf(forecastDate));

            System.out.println("Forecasts");
            System.out.println("=============================================");
            System.out.println("Id" + "\t"+ "Zip"+ "\t" + "City"+ "\t" + "State"+ "\t"+ "\t"+ "\t" + "Country"+ "\t"+ "\t" +"Temperature"+ "\t" + "Date");
            for (Forecast c: forecasts) {
                //System.out.println(c.getId() + "\t" + c.getCity_id()+ "\t" + c.getTemperature()+ "\t" + c.getDate()+ "\t" + c.getZipCode());
                var city = cityService.getCityById(c.getCity_id());
                //System.out.println(city.get().getId() + "\t" + city.get().getCityName());
                var state = stateService.getStateById(city.get().getState_id());
                //System.out.println(state.get().getId() + "\t" + state.get().getStateName());
                var country = countryService.getCountryById(state.get().getCountry_id());
                //System.out.println(country.get().getId() + "\t" + country.get().getCountryName());
                System.out.println(c.getId() + "\t"+ c.getZipCode()+ "\t" + city.get().getCityName()+ "\t" + state.get().getStateName()+ "\t" + country.get().getCountryName()+ "\t" + c.getTemperature()+ "C / "+((c.getTemperature()*1.8)+32)+"F"+ "\t" + c.getDate());
            }
        }
    }
}
