package pl.galushop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.exception.ValidationException;
import pl.galushop.component.ErrorMessages;
import pl.galushop.util.RegisterValidator;
import pl.galushop.component.MessageService;
import pl.galushop.component.Role;
import pl.galushop.dto.request.UserRequest;
import pl.galushop.dto.response.UserResponse;
import pl.galushop.mapper.UserMapper;
import pl.galushop.model.User;
import pl.galushop.repository.UserRepository;
import pl.galushop.util.ServiceValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ServiceValidator serviceValidator;
    private final RegisterValidator registerValidator;
    private final MessageService messageService;

    public UserResponse getUserResponse(User user) {
        return UserMapper.toUserResponse(user);
    }

    public UserResponse saveNewUser(UserRequest request) {
        serviceValidator.throwIfRequestIsNull(request, messageService.getMessage(ErrorMessages.INVALID_USER_REQUEST));

        User user = UserMapper.toUserModelFromRegister(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);

        List<String> validationFailures = registerValidator.validateErrors(user);
        if (validationFailures.isEmpty()) {
            return UserMapper.toUserResponse(userRepository.save(user));
        } else {
            throw new ValidationException(validationFailures);
        }
    }

    public UserResponse updateUser(UserRequest request, User user) {
        serviceValidator.throwIfRequestIsNull(request, messageService.getMessage(ErrorMessages.INVALID_USER_REQUEST));

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());

        if (request.password() != null && !request.password().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }

        return UserMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public UserResponse enableUser(User user) {
        user.setEnabled(true);
        return UserMapper.toUserResponse(userRepository.save(user));
    }
}
