package commands;

import collectionClasses.MovieGenre;
import collectionManagers.CollectionManager;
import connection.Response;
import connection.ResponseStatus;

import java.io.Serial;

public class  CountGreaterThanGenreCommand implements Action, CollectionEditor, Command {

    @Serial
    private static final long serialVersionUID = -2442949581870811398L;
    private MovieGenre genre;
    public CountGreaterThanGenreCommand(MovieGenre genre){this.genre = genre;}
    @Override
    public Response run() {
        return new Response(ResponseStatus.OK, CollectionManager.countGreaterThanGenre(genre));
    }

    @Override
    public String getCommandName() {
        return "count_greater_than_genre";
    }
}
