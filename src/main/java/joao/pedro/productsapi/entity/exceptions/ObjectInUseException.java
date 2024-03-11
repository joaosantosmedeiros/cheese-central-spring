package joao.pedro.productsapi.entity.exceptions;

public class ObjectInUseException extends RuntimeException {

    public ObjectInUseException() {
        super("Object already in use.");
    }

    public ObjectInUseException(String object) {
        super(object + " already in use.");
    }
}