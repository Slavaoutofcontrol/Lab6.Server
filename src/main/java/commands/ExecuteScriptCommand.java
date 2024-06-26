package commands;


import connection.Response;
import connection.ResponseStatus;

import java.io.Serial;
import java.util.ArrayList;


public class ExecuteScriptCommand implements Action,Command {

    @Serial
    private static final long serialVersionUID = -7431356007880416793L;
    private final ArrayList<Command> commandStack;
    public ExecuteScriptCommand(ArrayList<Command> commands){
        this.commandStack = commands;
    }

    @Override
    public Response run() {
        if (commandStack.isEmpty()) return new Response(ResponseStatus.ERROR, "The command queue is empty. ");
        StringBuilder output = new StringBuilder();
        commandStack.forEach(command -> output.append(command.run().getResponse()).append("\n"));
        return new Response(ResponseStatus.OK, output.substring(0, output.length() - 1));
    }

    @Override
    public String getCommandName() {
        return "execute_script";
    }

}