package commands;

import collectionClasses.Movie;
import collectionManagers.CollectionManager;
import connection.Response;
import connection.ResponseStatus;

import java.io.Serial;

public class AddIfMinCommand implements Action, CollectionEditor, Command {

    @Serial
    private static final long serialVersionUID = -4252936670992812458L;
    private Movie movie;
    public AddIfMinCommand(Movie movie) {
        this.movie = movie;
    }
    @Override
    public Response run() {
        return new Response(ResponseStatus.OK, CollectionManager.addIfMin(movie));
    }

    @Override
    public String getCommandName() {
        return "add_if_min";
    }
}
