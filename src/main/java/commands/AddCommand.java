package commands;


import collectionClasses.Movie;
import collectionManagers.CollectionManager;
import connection.Response;
import connection.ResponseStatus;

import java.io.Serial;


public class AddCommand implements Action, CollectionEditor,Command {

    @Serial
    private static final long serialVersionUID = 4884185358828753717L;
    private Movie movie;
    public AddCommand(Movie movie) {
        this.movie = movie;
    }
    @Override
    public Response run() {
        CollectionManager.add(movie);
        return new Response(ResponseStatus.OK, "Элемент успешно добавлен в коллекцию");
    }

    @Override
    public String getCommandName() {
        return "add";
    }
}
