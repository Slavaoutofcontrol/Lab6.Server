package commands;

import collectionClasses.Movie;
import collectionManagers.CollectionManager;
import connection.Response;
import connection.ResponseStatus;

import java.io.Serial;

public class AddIfMaxCommand implements Action, CollectionEditor, Command {

    @Serial
    private static final long serialVersionUID = -11081439549547967L;
    private Movie movie;
    public AddIfMaxCommand(Movie movie) {
        this.movie = movie;
    }
    @Override
    public Response run() {
        return new Response(ResponseStatus.OK, CollectionManager.addIfMax(movie));
    }

    @Override
    public String getCommandName() {
        return "add_if_max";
    }
}
