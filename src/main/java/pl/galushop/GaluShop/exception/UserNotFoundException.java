package pl.galushop.GaluShop.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class UserNotFoundException {
    private final List<String> UserNotFoundErrors;
}
