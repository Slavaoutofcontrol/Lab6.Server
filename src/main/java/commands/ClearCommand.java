package commands;

import collectionManagers.CollectionManager;
import connection.Response;
import connection.ResponseStatus;

import java.io.Serial;

public class ClearCommand implements Action, CollectionEditor, Command {

    @Serial
    private static final long serialVersionUID = -8925036261411386007L;
    public ClearCommand() {
    }
    @Override
    public Response run() {
        CollectionManager.clear();
        return new Response(ResponseStatus.OK,  "Коллекция успешно очищена");
    }

    @Override
    public String getCommandName() {
        return "clear";
    }
}
