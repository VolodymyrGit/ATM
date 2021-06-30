package volm.atm.exceptions;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String cardNumber) {
        super("User with this card number \"" + cardNumber + "\" already exist");
    }
}
