package pl.galushop.GaluShop.exception;

public class PaymentsNotFoundException extends RuntimeException{
    public PaymentsNotFoundException(String message){
        super(message);
    }
}
