package volm.atm.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String cardNumber) {
        super("Could not find user with this \"" + cardNumber + "\" card number in database");
    }
}
