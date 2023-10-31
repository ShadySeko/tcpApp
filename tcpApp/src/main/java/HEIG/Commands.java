package HEIG;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;


/**
 * This class implements the picoCLI commands our program will use.
 */
@Command(name = "DAIProtocol", mixinStandardHelpOptions = true, version = "DAI Protocol 1.0",
        description = "TODO")
public class Commands implements Callable<Integer> {


    @Option(names = {"-p", "-port"}, description = "Port to use for connexion", required = true)
    private int port = 1234; //default port


    @Option(names = {"-t", "-threads"}, description = "Number of threads server-side", required = true)
    private int threads = 4; //default number of threads

    @Command(name = "server", mixinStandardHelpOptions = true, version = "server 1.0",
            description = "Starts the server.")
    public Integer server() throws Exception {

        return null;
    }

    @Command(name = "client", mixinStandardHelpOptions = true, version = "client 1.0",
            description = "Starts the client.")
    public Integer client() throws Exception {

        return null;
    }


    @Override
    public Integer call() throws Exception {
        System.out.println("Please specify a valid command, use -h for help.");
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Commands()).execute(args);
        System.exit(exitCode);
    }


}
