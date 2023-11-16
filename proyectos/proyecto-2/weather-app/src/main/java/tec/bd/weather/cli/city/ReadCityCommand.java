package tec.bd.weather.cli.city;

import picocli.CommandLine;
import tec.bd.weather.ApplicationContext;
import tec.bd.weather.entity.City;

@CommandLine.Command(name = "city-read", aliases = {"cir"}, description = "read city by id")
public class ReadCityCommand implements Runnable{

    @CommandLine.Parameters(paramLabel = "<city Id>", description = "The city Id.", defaultValue = "0")
    private int cityId;

    @Override
    public void run() {
        System.out.println("Read City. city Id: " + cityId);

        var appContext = new ApplicationContext();
        var cityService = appContext.getCityService();

        // Es que no se pasó un country Id como parametro al comando
        if (cityId == 0) {
            var citys = cityService.getAllCitys();

            System.out.println("Citys");
            System.out.println("=============================================");
            for (City c: citys) {
                System.out.println(c.getId() + "\t" + c.getCityName()+ "\t" + c.getZipCode()+ "\t" + c.getState_id());
            }
        } else {

            var city = cityService.getCityById(cityId);
            if (city.isPresent()) {
                System.out.println("City");
                System.out.println("=============================================");
                System.out.println(city.get().getId() + "\t" + city.get().getCityName()+ "\t" + city.get().getZipCode()+ "\t" + city.get().getState_id());
            } else {
                System.out.println("City Id: " + cityId + " not found.");
            }
        }
    }
}
