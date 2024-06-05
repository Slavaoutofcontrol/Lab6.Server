package commands;
import connection.Response;

import java.io.Serializable;

public interface Command extends Serializable{
    Response run();
    String getCommandName();
}
