package commands;


import collectionManagers.CollectionManager;
import connection.Response;
import connection.ResponseStatus;

import java.io.Serial;

public class InfoCommand implements Action,Command {

    @Serial
    private static final long serialVersionUID = -6035633453588208723L;

    @Override
    public Response run() {
        return new Response(ResponseStatus.OK, CollectionManager.info());
    }

    @Override
    public String getCommandName() {
        return "info";
    }
}
