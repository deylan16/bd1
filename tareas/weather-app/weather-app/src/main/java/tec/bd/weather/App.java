package tec.bd.weather;

import picocli.CommandLine;
import tec.bd.weather.cli.MainCommand;

//cf "United States" "Chicago" 10002 "2023-01-02 14:23:00" 23
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        CommandLine cmd = new CommandLine(new MainCommand());
        cmd.setExecutionStrategy(new CommandLine.RunAll());
        cmd.execute(args);

        if (args.length == 0){
            cmd.usage(System.out);
        }


    }
}
