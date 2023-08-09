package tec.bd.weather;

import picocli.CommandLine;

@CommandLine.Command(name = "by-zip",description = "Get weather for a Zip Code ")
public class WeatherByZipCodeCommand implements Runnable {

    @CommandLine.Parameters(paramLabel = "<zip code>",description = "The zip code")
    private String zipCode;


    @Override
    public void run() {
        System.out.println("By Zip Code:" + zipCode);

        try {
            WeatherService weatherService = new WeatherServiceImpl();
            System.out.println(weatherService.getByZipCodeTemperature(zipCode));
        }catch (Exception e){
            System.err.println(zipCode + " id not soported");
        }
    }
}
