package tec.bd.weather.cli.city;


import picocli.CommandLine;
import tec.bd.weather.ApplicationContext;
import tec.bd.weather.entity.City;

@CommandLine.Command(name = "city-update", aliases = {"ciu"}, description = "update city by id")


public class UpdateCityCommand implements Runnable {

    @CommandLine.Parameters(paramLabel = "<city Id>", description = "The city Id.", defaultValue = "0")
    private int cityId;

    @CommandLine.Parameters(paramLabel = "<city name>", description = "The city name")
    private String cityName;

    @CommandLine.Parameters(paramLabel = "<state id>", description = "The state id")
    private int state_id;

    @CommandLine.Parameters(paramLabel = "<zip Code>", description = "The zip code")
    private int zipCode;


    @Override
    public void run() {
        System.out.println("city-update");

            // System.out.println("country-create");
            //creasr pais
        var appContext = new ApplicationContext();
        var CityService = appContext.getCityService();
        var oldCity = new City(cityId,cityName,zipCode,state_id);
        var UpdateCity = CityService.updateCity(oldCity);

        System.out.println("Update City: "+ UpdateCity.getId() + ", " + UpdateCity.getCityName()+ ", " + UpdateCity.getZipCode()+ ", " + UpdateCity.getState_id());


    }
}
