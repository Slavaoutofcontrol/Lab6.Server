package commands;


import collectionClasses.Movie;
import collectionManagers.CollectionManager;
import connection.Response;
import connection.ResponseStatus;

import java.io.Serial;

public class UpdateIdCommand implements Action, CollectionEditor, Command {

    @Serial
    private static final long serialVersionUID = -620855360289409781L;
    private Movie movie;
    /**
     * Constructor of class
     */
    public UpdateIdCommand(Movie movie) {
        this.movie = movie;
    }
    @Override
    public Response run() {
        return new Response(ResponseStatus.OK, CollectionManager.updateId(movie));
    }

    @Override
    public String getCommandName() {
        return "update_id";
    }
}
