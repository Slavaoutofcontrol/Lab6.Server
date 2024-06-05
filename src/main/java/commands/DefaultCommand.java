package commands;


import connection.Response;
import connection.ResponseStatus;

import java.io.Serial;

public class DefaultCommand implements Action,Command {
    @Serial
    private static final long serialVersionUID = -4745382075765516610L;
    @Override
    public Response run() {
        return new Response(ResponseStatus.INFO, "Неизвестная команда. Введите 'help' чтобы получить список доступных команд. ");
    }

    @Override
    public String getCommandName() {
        return null;
    }
}
