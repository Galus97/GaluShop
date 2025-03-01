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

/**
 * Service class responsible for managing user-related operations.
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageService messageService;

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The retrieved user.
     * @throws IllegalArgumentException if the userId is null or less than 1.
     * @throws UsernameNotFoundException if no user is found with the given ID.
     */
    public User getUser(Long userId){
        if (userId == null || userId < 1) {
            throw new IllegalArgumentException(messageService.getMessage("error.invalidUserId", userId));
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(messageService.getMessage("error.userNotFound", userId)));
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userId The ID of the user to delete.
     * @throws IllegalArgumentException if the userId is null or less than 1.
     * @throws UsernameNotFoundException if no user is found with the given ID.
     */
    public void deleteUser(Long userId){
        if (userId == null || userId < 1) {
            throw new IllegalArgumentException(messageService.getMessage("error.invalidUserId", userId));
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(messageService.getMessage("error.userNotFound", userId)));
        userRepository.delete(user);
    }

    /**
     * Updates an existing user's details.
     * If a new password is provided, it will be updated; otherwise, the old password remains unchanged.
     *
     * @param userRequest The request object containing updated user details.
     * @throws IllegalArgumentException if the userRequest is invalid.
     * @throws UsernameNotFoundException if no user is found with the given ID.
     */
    @Transactional
    public void updateUser(UserRequest userRequest){
        if (userRequest.getUserId() == null || userRequest.getUserId() < 1) {
            throw new IllegalArgumentException(messageService.getMessage("error.invalidUserRequest"));
        }
        User existingUser = userRepository.findById(userRequest.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException(messageService.getMessage("error.userNotFound", userRequest.getUserId())));

        existingUser.setFirstName(userRequest.getFirstName());
        existingUser.setLastName(userRequest.getLastName());
        existingUser.setEmail(userRequest.getEmail());

        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        userRepository.save(existingUser);
    }
}
