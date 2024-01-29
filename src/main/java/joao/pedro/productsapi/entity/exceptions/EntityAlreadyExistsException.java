package joao.pedro.productsapi.entity.exceptions;

public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException() {
        super("Entity already exists.");
    }

    public EntityAlreadyExistsException(String entity) {
        super(entity + " already exists.");
    }
}
