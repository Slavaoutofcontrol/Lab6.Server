package commands;


import collectionManagers.CollectionManager;
import connection.Response;
import connection.ResponseStatus;

import java.io.Serial;

public class ShowCommand implements Action, Command {

    @Serial
    private static final long serialVersionUID = 2237136524858086694L;
    public ShowCommand(){}
    @Override
    public Response run() {
        return new Response(ResponseStatus.OK, CollectionManager.show());
    }

    @Override
    public String getCommandName() {
        return "show";
    }
}
