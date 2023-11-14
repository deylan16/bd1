package tec.bd.weather.cli.city;


import picocli.CommandLine;
import tec.bd.weather.ApplicationContext;

@CommandLine.Command(name = "city-create", aliases = {"cic"}, description = "Create new city for country")
public class CreateCityCommand implements Runnable{

    @CommandLine.Parameters(paramLabel = "<city name>", description = "The city name")
    private String cityName;

    @CommandLine.Parameters(paramLabel = "<state id>", description = "The state id")
    private int state_id;

    @CommandLine.Parameters(paramLabel = "<zip Code>", description = "The zip code")
    private int zipCode;


    @Override
    public void run() {
       // System.out.println("country-create");
        //creasr pais
        var appContext = new ApplicationContext();
        var cityService = appContext.getCityService();
        var newCity =cityService.newCity(cityName,zipCode,state_id);

        System.out.println("New City: "+ newCity.getId() + ", " + newCity.getCityName()+ ", " + newCity.getZipCode()+ ", " + newCity.getState_id());
    }
}
