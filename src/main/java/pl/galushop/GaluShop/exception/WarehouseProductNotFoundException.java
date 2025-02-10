package pl.galushop.GaluShop.exception;

public class WarehouseProductNotFoundException extends RuntimeException{
    public WarehouseProductNotFoundException(String message){
        super(message);
    }
}
