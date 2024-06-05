package commands;

import collectionManagers.CollectionManager;
import connection.Response;
import connection.ResponseStatus;

import java.io.Serial;

public class RemoveByIdCommand implements Action, CollectionEditor, Command {

    @Serial
    private static final long serialVersionUID = 904823743523031940L;
    private long id;
    public RemoveByIdCommand(long id) {
        this.id = id;
    }
    @Override
    public Response run() {
        return new Response(ResponseStatus.OK, CollectionManager.removeById(id));
    }

    @Override
    public String getCommandName() {
        return "remove_by_id";
    }
}
