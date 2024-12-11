package pl.galushop.GaluShop.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RegisterValidator {

    private final UserRepository userRepository;

    public List<String> validateUserErrors(User user) {
        List<String> errors = new ArrayList<>();
        Optional<User> userExistByEmail = userRepository.findByEmail(user.getEmail());
        if (userExistByEmail.isPresent()) {
            errors.add("Ten adres email jest już używany. Wpisz inny adres email");
        }
        return errors;
    }
}
