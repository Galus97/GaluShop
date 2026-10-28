package pl.galushop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.galushop.component.ErrorMessages;
import pl.galushop.component.MessageService;
import pl.galushop.component.Role;
import pl.galushop.dto.VerificationToken;
import pl.galushop.dto.request.UserRequest;
import pl.galushop.dto.response.UserResponse;
import pl.galushop.exception.EntityNotFoundException;
import pl.galushop.exception.ValidationException;
import pl.galushop.mapper.UserMapper;
import pl.galushop.model.User;
import pl.galushop.repository.UserRepository;
import pl.galushop.util.RegisterValidator;
import pl.galushop.util.ServiceValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ServiceValidator serviceValidator;
    private final RegisterValidator registerValidator;
    private final MessageService messageService;
    private final EmailService emailService;

    public UserResponse saveNewUser(UserRequest request, Role role) throws ValidationException {
        serviceValidator.throwIfRequestIsNull(request, messageService.getMessage(ErrorMessages.INVALID_USER_REQUEST));

        User user = UserMapper.toUserModelFromRegister(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(role);

        List<String> validationFailures = registerValidator.validateErrors(user);
        if (validationFailures.isEmpty()) {
            User savedUser = userRepository.save(user);
            emailService.sendEmail(savedUser.getEmail());
            return UserMapper.toUserResponse(savedUser);
        } else {
            throw new ValidationException(validationFailures);
        }
    }

    public void verifyUser(String email, String enteredCode) throws ValidationException{
        serviceValidator.throwIfEmailIsInvalid(email, ErrorMessages.EMAIL_IS_INVALID);

        VerificationToken token = emailService.getVerificationToken(email);

        if (token == null) {
            throw new ValidationException(List.of(messageService.getMessage(ErrorMessages.TOKEN_IS_NULL)));
        }

        if (token.isExpired()) {
            emailService.evictVerificationToken(email);
            throw new ValidationException(List.of(messageService.getMessage(ErrorMessages.TOKEN_IS_EXPIRED)));
        }

        if (!token.code().equals(enteredCode)) {
            throw new ValidationException(List.of(messageService.getMessage(ErrorMessages.ENTERED_CODE_INVALID)));
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageService.getMessage(ErrorMessages.USER_NOT_FOUND)));

        user.setEnabled(true);
        userRepository.save(user);

        emailService.evictVerificationToken(email);
    }
}
