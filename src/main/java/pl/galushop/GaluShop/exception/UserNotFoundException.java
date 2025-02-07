package pl.galushop.GaluShop.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException (String message){
        super(message);
    }
}
