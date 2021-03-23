package chuyong.aegis.exception;

public class CommandArgumentFailedException extends RuntimeException{
    public CommandArgumentFailedException(String arguments){
        super("Following arguments can't be parsed: "+arguments);
    }
}
