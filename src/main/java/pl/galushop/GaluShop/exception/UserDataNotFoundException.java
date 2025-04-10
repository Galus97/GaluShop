package pl.galushop.GaluShop.exception;

public class UserDataNotFoundException extends RuntimeException {
    public UserDataNotFoundException(String message) {
        super(message);
    }
}
