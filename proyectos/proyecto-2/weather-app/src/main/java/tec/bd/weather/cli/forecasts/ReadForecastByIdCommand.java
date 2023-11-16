package tec.bd.weather.cli.forecasts;

import picocli.CommandLine;
import tec.bd.weather.ApplicationContext;
import tec.bd.weather.entity.Forecast;

@CommandLine.Command(name = "forecast-read-fid", aliases = {"frid"}, description = "read forecast by id")
public class ReadForecastByIdCommand implements Runnable{

    @CommandLine.Parameters(paramLabel = "<forecast Id>", description = "The forecast Id.", defaultValue = "0")
    private int forecastId;

    @Override
    public void run() {
        System.out.println("Read Forecast. forecast Id: " + forecastId);

        var appContext = new ApplicationContext();
        var forecastService = appContext.getForecastService();

        var cityService = appContext.getCityService();
        var stateService = appContext.getStateService();
        var countryService = appContext.getCountryService();

        // Es que no se pasó un country Id como parametro al comando
        if (forecastId == 0) {
            var forecasts = forecastService.getAllForecasts();

            System.out.println("Forecasts");
            System.out.println("=============================================");
            for (Forecast c: forecasts) {
                System.out.println(c.getId() + "\t" + c.getCity_id()+ "\t" + c.getTemperature()+ "\t" + c.getDate()+ "\t" + c.getZipCode());
            }
        } else {

            var forecast = forecastService.getForecastById(forecastId);
            if (forecast.isPresent()) {
                System.out.println("Forecast");
                System.out.println("=============================================");

                System.out.println("Zip"+ "\t" + "City"+ "\t" + "State"+ "\t"+ "\t"+ "\t" + "Country");

                var city = cityService.getCityById(forecast.get().getCity_id());
                var state = stateService.getStateById(city.get().getState_id());
                var country = countryService.getCountryById(state.get().getCountry_id());
                System.out.println(forecast.get().getZipCode()+ "\t" + city.get().getCityName()+ "\t" + state.get().getStateName()+ "\t" + country.get().getCountryName());

                System.out.println("=============");
                System.out.println("Id" + "\t" +"Temperature"+ "\t" + "Date");
                System.out.println(forecast.get().getId() + "\t" + forecast.get().getTemperature()+ "C / "+((forecast.get().getTemperature()*1.8)+32)+"F"+ "\t" + forecast.get().getDate());


            } else {
                System.out.println("Forecast Id: " + forecastId + " not found.");
            }
        }
    }
}
