package tec.bd.weather.cli.state;

import picocli.CommandLine;
import tec.bd.weather.ApplicationContext;
import tec.bd.weather.entity.Country;
import tec.bd.weather.entity.State;

@CommandLine.Command(name = "state-read", aliases = {"sr"}, description = "read state by id")
public class ReadStateCommand implements Runnable{

    @CommandLine.Parameters(paramLabel = "<state Id>", description = "The state Id.", defaultValue = "0")
    private int stateId;

    @Override
    public void run() {
        System.out.println("Read State. State Id: " + stateId);

        var appContext = new ApplicationContext();
        var stateService = appContext.getStateService();

        // Es que no se pasó un country Id como parametro al comando
        if (stateId == 0) {
            var states = stateService.getAllStates();

            System.out.println("States");
            System.out.println("=============================================");
            for (State c: states) {
                System.out.println(c.getId() + "\t" + c.getStateName()+ "\t" + c.getCountry_id());
            }
        } else {

            var state = stateService.getStateById(stateId);
            if (state.isPresent()) {
                System.out.println("State");
                System.out.println("=============================================");
                System.out.println(state.get().getId() + "\t" + state.get().getStateName()+ "\t" + state.get().getCountry_id());
            } else {
                System.out.println("State Id: " + stateId + " not found.");
            }
        }
    }
}
