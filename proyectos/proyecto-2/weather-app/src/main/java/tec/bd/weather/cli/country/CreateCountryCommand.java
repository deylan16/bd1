package tec.bd.weather.cli.country;


import picocli.CommandLine;
import tec.bd.weather.ApplicationContext;

@CommandLine.Command(name = "country-create", aliases = {"cc"}, description = "Create new country for a city")
public class CreateCountryCommand implements Runnable{

    @CommandLine.Parameters(paramLabel = "<country name>", description = "The country name")
    private String countryName;

    @Override
    public void run() {
       // System.out.println("country-create");
        //creasr pais
        var appContext = new ApplicationContext();
        var countryService = appContext.getCountryService();
        var newCountry =countryService.newCountry(countryName);

        System.out.println("New Country: "+ newCountry.getId() + ", " + newCountry.getCountryName());
    }
}
