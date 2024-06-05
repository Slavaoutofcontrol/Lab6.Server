package commands;

import collectionClasses.Movie;
import collectionManagers.CollectionManager;
import connection.Response;
import connection.ResponseStatus;

import java.io.Serial;

public class RemoveGreaterCommand implements Action, CollectionEditor, Command {

    @Serial
    private static final long serialVersionUID = -6679190409592074342L;
    private Movie movie;
    public RemoveGreaterCommand(Movie movie){
        this.movie = movie;
    }
    @Override
    public Response run() {
        return new Response(ResponseStatus.OK, CollectionManager.removeGreater(movie));
    }

    @Override
    public String getCommandName() {
        return "remove_greater";
    }
}