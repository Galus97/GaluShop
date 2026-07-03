package pl.galushop.GaluShop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.galushop.GaluShop.component.MessageService;

@Component
@RequiredArgsConstructor
public class ServiceValidator {
    private final MessageService messageService;

    public void throwIfRequestIsNull(Object request, String errorMessageKey) {
        if (request == null) {
            throw new IllegalArgumentException(messageService.getMessage(errorMessageKey));
        }
    }

    public void throwIfIdIsNotValid(Long id, String errorMessageKey) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException(messageService.getMessage(errorMessageKey));
        }
    }

    private void throwIfEmailIsInvalid(String email, String errorMessageKey) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(messageService.getMessage(errorMessageKey, email));
        }
    }
}
