package volm.atm.exceptions;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(Class clazz) {
        super(clazz.getSimpleName() + " not found");
    }
}
