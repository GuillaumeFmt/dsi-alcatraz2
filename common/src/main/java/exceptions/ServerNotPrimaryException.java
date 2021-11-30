package exceptions;

public class ServerNotPrimaryException extends Exception{
    public ServerNotPrimaryException(String s){
        super(s);
    }
}
