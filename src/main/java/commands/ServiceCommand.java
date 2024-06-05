package commands;

import collectionManagers.CollectionManager;
import connection.Response;
import connection.ResponseStatus;

import java.io.Serial;

public class ServiceCommand implements Action, Command{
    @Serial
    private static final long serialVersionUID = 8900955114697874298L;
    private final String command;

    public ServiceCommand(String command) {
        this.command = command;
    }
    @Override
    public Response run() {
        String[] splitCommand = command.split(" ");
        String commandPart = splitCommand[0];
        String arg = splitCommand[1];
        return switch (commandPart) {
            case "check_id" -> {
                long id = Long.parseLong(arg);
                boolean isPresented = CollectionManager.isContainsId(id);
                yield new Response(ResponseStatus.INFO, Boolean.toString(isPresented));
            }
            default -> new Response(ResponseStatus.INFO, "");
        };
    }

    @Override
    public String getCommandName() {
        return "service";
    }
}