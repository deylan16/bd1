package tec.bd.weather.cli.country;


import picocli.CommandLine;
import tec.bd.weather.ApplicationContext;
import tec.bd.weather.entity.Country;

@CommandLine.Command(name = "country-update", aliases = {"cu"}, description = "update country by id")


public class UpdateCountryCommand implements Runnable {

    @CommandLine.Parameters(paramLabel = "<country Id>", description = "The country Id.", defaultValue = "0")
    private int countryId;

    @CommandLine.Parameters(paramLabel = "<country name>", description = "The country name")
    private String countryName;

    @Override
    public void run() {
        System.out.println("country-update");

            // System.out.println("country-create");
            //creasr pais
        var appContext = new ApplicationContext();
        var countryService = appContext.getCountryService();
        var oldCountry = new Country(countryId,countryName);
        var UpdateCountry =countryService.updateCountry(oldCountry);

        System.out.println("Update Country: "+ UpdateCountry.getId() + ", " + UpdateCountry.getCountryName());


    }
}
