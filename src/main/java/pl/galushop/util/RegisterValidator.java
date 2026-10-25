package pl.galushop.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.galushop.component.ErrorMessages;
import pl.galushop.component.MessageService;
import pl.galushop.repository.UserRepository;
import pl.galushop.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RegisterValidator {
    private final UserRepository userRepository;
    private final MessageService messageService;

    public List<String> validateErrors(User user) {
        List<String> errors = new ArrayList<>();

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            errors.add(messageService.getMessage(ErrorMessages.EMAIL_IS_ALREADY_USED));
        }
        return errors;
    }
}
