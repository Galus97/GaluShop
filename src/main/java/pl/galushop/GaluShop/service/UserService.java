package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.UserRequest;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageService messageService;

    public User getUser(Long userId){
        if (userId == null || userId < 1) {
            throw new IllegalArgumentException(messageService.getMessage("error.invalidUserId", userId));
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(messageService.getMessage("error.userNotFound", userId)));
    }

    public void deleteUser(Long userId){
        if (userId == null || userId < 1) {
            throw new IllegalArgumentException(messageService.getMessage("error.invalidUserId", userId));
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(messageService.getMessage("error.userNotFound", userId)));
        userRepository.delete(user);
    }

    @Transactional
    public void updateUser(UserRequest userRequest){
        if(userRequest.getUserId() == null || userRequest.getUserId() < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidUserRequest"));
        }
        User existingUser = userRepository.findById(userRequest.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException(messageService.getMessage("error.userNotFound", userRequest.getUserId())));

        existingUser.setFirstName(userRequest.getFirstName());
        existingUser.setLastName(userRequest.getLastName());
        existingUser.setEmail(userRequest.getEmail());
        if(userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()){
            existingUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        userRepository.save(existingUser);
    }
}
