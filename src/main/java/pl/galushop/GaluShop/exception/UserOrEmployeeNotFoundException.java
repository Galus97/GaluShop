package pl.galushop.GaluShop.exception;

public class UserOrEmployeeNotFoundException extends RuntimeException{
    public UserOrEmployeeNotFoundException(String message){
        super(message);
    }
}
