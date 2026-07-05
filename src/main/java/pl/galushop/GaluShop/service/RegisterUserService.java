package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.component.ErrorMessages;
import pl.galushop.GaluShop.util.RegisterValidator;
import pl.galushop.GaluShop.dto.request.UserRequest;
import pl.galushop.GaluShop.dto.response.UserResponse;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.exception.ValidationException;
import pl.galushop.GaluShop.repository.UserRepository;
import pl.galushop.GaluShop.util.ServiceValidator;

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
    private final ServiceValidator serviceValidator;

    /**
     * Registers a new user by validating the provided data, encoding the password,
     * saving the user entity to the database, and sending a verification email.
     *
     * @param userRequest The DTO containing registration details (name, email, password, etc.)
     * @return A response DTO with saved user data.
     * @throws ValidationException      If any validation error occurs during registration.
     * @throws IllegalArgumentException If the request object is null.
     */
    public UserResponse saveNewUser(UserRequest userRequest) throws ValidationException {
        serviceValidator.throwIfRequestIsNull(userRequest, ErrorMessages.INVALID_USER_REQUEST);
        User user = buildUserFromRequest(userRequest);

        List<String> validationFailures = registerValidator.validateErrors(user);
        if (validationFailures.isEmpty()) {
            emailService.sendEmail(userRequest.getEmail());
            return UserResponse.fromEntity(userRepository.save(user));
        } else {
            throw new ValidationException(validationFailures);
        }
    }

    /**
     * Builds a User entity from the provided request data.
     * The password is encoded and a unique email verification code is generated.
     *
     * @param userRequest The request containing registration data.
     * @return A new User entity ready to be persisted.
     */
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