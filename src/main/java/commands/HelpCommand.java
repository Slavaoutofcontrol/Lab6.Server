package commands;

import collectionClasses.CommandType;
import connection.Response;
import connection.ResponseStatus;

import java.io.Serial;
import java.util.Arrays;
import java.util.stream.Collectors;

public class HelpCommand implements Action,Command {

    @Serial
    private static final long serialVersionUID = 4831338180725440541L;
    public HelpCommand(){
    }
    @Override
    public Response run() {
        return new Response(ResponseStatus.OK, Arrays.stream(CommandType.values()).
                map(CommandType::getDescription).
                filter(description -> !description.isEmpty()).
                collect(Collectors.joining("\n")));
    }

    @Override
    public String getCommandName() {
        return "help";
    }
}
