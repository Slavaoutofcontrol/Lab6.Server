package commands;

import collectionManagers.CollectionManager;
import connection.Response;
import connection.ResponseStatus;

import java.io.Serial;

public class GroupCountingByDirectorCommand implements Action, CollectionEditor, Command {

    @Serial
    private static final long serialVersionUID = -8936229594545431248L;
    public GroupCountingByDirectorCommand(){}
    @Override
    public Response run() {
        return new Response(ResponseStatus.OK, CollectionManager.groupCountingByDirector());
    }

    @Override
    public String getCommandName() {
        return "count_less_than_mpaa_rating";
    }
}