package tec.bd.weather.cli.state;


import picocli.CommandLine;
import tec.bd.weather.ApplicationContext;

@CommandLine.Command(name = "state-create", aliases = {"sc"}, description = "Create new state for country")
public class CreateStateCommand implements Runnable{

    @CommandLine.Parameters(paramLabel = "<state name>", description = "The state name")
    private String stateName;

    @CommandLine.Parameters(paramLabel = "<country id>", description = "The country id")
    private int country_id;

    @Override
    public void run() {
       // System.out.println("country-create");
        //creasr pais
        var appContext = new ApplicationContext();
        var stateService = appContext.getStateService();
        var newState =stateService.newState(stateName,country_id);

        System.out.println("New State: "+ newState.getId() + ", " + newState.getStateName()+ ", " + newState.getCountry_id());
    }
}
