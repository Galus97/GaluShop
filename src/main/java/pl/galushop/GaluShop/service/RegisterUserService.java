package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.component.RegisterValidator;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.exception.ValidationException;
import pl.galushop.GaluShop.repository.UserRepository;

import java.util.List;

@Service
//@Transactional
@RequiredArgsConstructor
public class RegisterUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegisterValidator registerValidator;

    public void saveNewUser(User user) throws ValidationException {
        List<String> validationFailures = registerValidator.validateUserErrors(user);
        if (validationFailures.isEmpty()) {
            user.setUserId(null);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } else {
            throw new ValidationException(validationFailures);
        }
    }
}
