package tec.bd.weather.cli.state;


import picocli.CommandLine;
import tec.bd.weather.ApplicationContext;
import tec.bd.weather.entity.Country;
import tec.bd.weather.entity.State;

@CommandLine.Command(name = "state-update", aliases = {"su"}, description = "update state by id")


public class UpdateStateCommand implements Runnable {

    @CommandLine.Parameters(paramLabel = "<state Id>", description = "The state Id.", defaultValue = "0")
    private int stateId;

    @CommandLine.Parameters(paramLabel = "<state name>", description = "The state name")
    private String stateName;

    @CommandLine.Parameters(paramLabel = "<country id>", description = "The country id")
    private int country_id;


    @Override
    public void run() {
        System.out.println("state-update");

            // System.out.println("country-create");
            //creasr pais
        var appContext = new ApplicationContext();
        var stateService = appContext.getStateService();
        var oldState = new State(stateId,stateName,country_id);
        var UpdateState =stateService.updateState(oldState);

        System.out.println("Update State: "+ UpdateState.getId() + ", " + UpdateState.getStateName()+ ", " + UpdateState.getCountry_id());


    }
}
