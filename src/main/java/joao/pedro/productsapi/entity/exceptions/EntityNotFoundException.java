package joao.pedro.productsapi.entity.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() {
        super("Entity not found.");
    }

    public EntityNotFoundException(String entity) {
        super(entity + " not found.");
    }
}
