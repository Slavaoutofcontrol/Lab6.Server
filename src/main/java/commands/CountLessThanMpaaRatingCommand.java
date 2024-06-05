package commands;

import collectionClasses.MpaaRating;
import collectionManagers.CollectionManager;
import connection.Response;
import connection.ResponseStatus;

import java.io.Serial;

public class CountLessThanMpaaRatingCommand implements Action, CollectionEditor, Command {

    @Serial
    private static final long serialVersionUID = -246712803353855092L;
    private MpaaRating mpaaRating;
    public CountLessThanMpaaRatingCommand(MpaaRating mpaaRating){this.mpaaRating = mpaaRating;}

    @Override
    public Response run() {
        return new Response(ResponseStatus.OK, CollectionManager.countLessThanMpaaRating(mpaaRating));
    }

    @Override
    public String getCommandName() {
        return "count_less_than_mpaa_rating";
    }
}
