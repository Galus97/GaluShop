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
}
