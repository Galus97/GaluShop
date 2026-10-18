package pl.galushop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.component.ErrorMessages;
import pl.galushop.component.MessageService;
import pl.galushop.component.Role;
import pl.galushop.dto.request.UserRegisterRequest;
import pl.galushop.dto.response.UserResponse;
import pl.galushop.mapper.UserMapper;
import pl.galushop.model.User;
import pl.galushop.repository.UserRepository;
import pl.galushop.util.ServiceValidator;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ServiceValidator serviceValidator;
    private final MessageService messageService;

    public UserResponse getUserResponse(User user) {
        return UserMapper.toUserResponse(user);
    }

    public UserResponse saveNewUser(UserRegisterRequest request) {
        serviceValidator.throwIfRequestIsNull(request, messageService.getMessage(ErrorMessages.INVALID_USER_REQUEST));

        User user = UserMapper.toUserModelFromRegister(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);

        return UserMapper.toUserResponse(userRepository.save(user));
    }
}
