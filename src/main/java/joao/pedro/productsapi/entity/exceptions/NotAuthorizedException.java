package joao.pedro.productsapi.entity.exceptions;

public class NotAuthorizedException extends RuntimeException{
    public NotAuthorizedException() {
        super("Not authorized.");
    }
}
