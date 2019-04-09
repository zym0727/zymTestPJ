package zym.exception;

/**
 * @author zym
 * *
 */
public class MessageException extends RuntimeException {

    public MessageException(){
    }

    public MessageException(String message){
        super(message);
    }
}
