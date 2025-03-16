package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.component.RegisterValidator;
import pl.galushop.GaluShop.dto.UserRequest;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.exception.ValidationException;
import pl.galushop.GaluShop.repository.UserRepository;

import java.util.List;

/**
 * Service class handling user registration process.
 * This includes validation, password encoding, saving the user to the database, and sending a verification email.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class RegisterUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegisterValidator registerValidator;
    private final EmailService emailService;

    /**
     * Registers a new user by validating input data, encoding the password,
     * saving the user to the database, and sending a verification email.
     *
     * @param userRequest The request object containing user registration details.
     * @return The created User
     * @throws ValidationException if the validation fails.
     */
    public User saveNewUser(UserRequest userRequest) throws ValidationException {
        User user = buildUserFromRequest(userRequest);

        List<String> validationFailures = registerValidator.validateErrors(user);
        if (validationFailures.isEmpty()) {
            emailService.sendEmail(userRequest.getEmail());
            return userRepository.save(user);
        } else {
            throw new ValidationException(validationFailures);
        }
    }

    private User buildUserFromRequest(UserRequest userRequest) {
        return User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .emailCode(emailService.getVerificationCode(userRequest.getEmail()))
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .build();
    }
}
