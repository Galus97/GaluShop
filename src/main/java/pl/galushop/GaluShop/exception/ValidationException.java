package pl.galushop.GaluShop.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ValidationException extends Exception {
    private final List<String> validationErrors;
}
