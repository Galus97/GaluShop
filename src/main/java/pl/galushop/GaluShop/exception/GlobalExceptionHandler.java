package pl.galushop.GaluShop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.galushop.GaluShop.component.ErrorMessages;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEmployeeNotFoundException(EmployeeNotFoundException e) {
        return getMapResponseEntity(e);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleOrderNotFoundException(OrderNotFoundException e) {
        return getMapResponseEntity(e);
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlerPaymentsNotFoundException(PaymentNotFoundException e) {
        return getMapResponseEntity(e);
    }

    @ExceptionHandler(ProductImagesNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductImagesNotFoundException(ProductImagesNotFoundException e) {
        return getMapResponseEntity(e);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFoundException(ProductNotFoundException e) {
        return getMapResponseEntity(e);
    }

    @ExceptionHandler(UserDataNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserDataNotFoundException(UserDataNotFoundException e) {
        return getMapResponseEntity(e);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException e) {
        return getMapResponseEntity(e);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException e) {
        return getMapResponseEntity(e);
    }

    @ExceptionHandler(WarehouseProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleWarehouseProductNotFoundException(WarehouseProductNotFoundException e) {
        return getMapResponseEntity(e);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    private static ResponseEntity<Map<String, String>> getMapResponseEntity(RuntimeException e) {
        Map<String, String> response = new HashMap<>();
        response.put(ErrorMessages.ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
