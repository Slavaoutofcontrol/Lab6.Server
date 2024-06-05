package connection;

import collectionClasses.Movie;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Request implements Serializable {

    @Serial
    private static final long serialVersionUID = -1309985392611975187L;
    private String commandName;
    private String args = "";
    private Movie movie = null;
    public Request(ResponseStatus responseStatus, String commandName, Movie movie){
        this.commandName = commandName.trim();
    }
    public Request(String commandName, String args){
        this.commandName = commandName.trim();
        this.args = args.trim();
    }
    public Request(String commandName, Movie movie){
        this.commandName = commandName.trim();
        this.movie = movie;
    }
    public Request(String commandName, String args, Movie movie){
        this.commandName = commandName.trim();
        this.args = args.trim();
        this.movie = movie;
    }
    public boolean isEmpty(){
        return commandName.isEmpty() && args.isEmpty() && movie == null;
    }
    public String getCommandName(){
        return commandName;
    }
    public String getArgs() {
        return args;
    }
    public Movie getMovie() {
        return movie;
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Request request)) return false;
        return Objects.equals(commandName, request.commandName) && Objects.equals(args, request.args) && Objects.equals(movie, request.movie);
    }
    @Override
    public int hashCode(){
        return Objects.hash(commandName, args, movie);
    }
    @Override
    public String toString(){
        return "Request[" + commandName +
                (args.isEmpty()
                        ? ""
                        : ", " + args) +
                ((movie == null)
                        ? "]"
                        : ", " + movie + "]");
    }
}