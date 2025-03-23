package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.component.ErrorMessages;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.request.UserRequest;
import pl.galushop.GaluShop.dto.response.UserResponse;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.exception.UserNotFoundException;
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
    public User getUserEntity(Long userId){
        throwIfIdIsInvalid(userId, ErrorMessages.INVALID_USER_ID);
        return getUserOrThrow(userId);
    }

    public UserResponse getUserResponse(Long userId){
        throwIfIdIsInvalid(userId, ErrorMessages.INVALID_USER_ID);
        return UserResponse.fromEntity(getUserOrThrow(userId));
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userId The ID of the user to delete.
     * @throws IllegalArgumentException if the userId is null or less than 1.
     * @throws UsernameNotFoundException if no user is found with the given ID.
     */
    public void deleteUser(Long userId){
        throwIfIdIsInvalid(userId, ErrorMessages.INVALID_USER_ID);

        userRepository.delete(getUserOrThrow(userId));
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
    public UserResponse updateUser(UserRequest userRequest){
        throwIfIdIsInvalid(userRequest.getUserId(), ErrorMessages.INVALID_USER_ID);
        
        User existingUser = getUserOrThrow(userRequest.getUserId());

        existingUser.setFirstName(userRequest.getFirstName());
        existingUser.setLastName(userRequest.getLastName());
        existingUser.setEmail(userRequest.getEmail());

        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        return UserResponse.fromEntity(userRepository.save(existingUser));
    }

    //Used in OrderService -> getAllOrdersByUser
    public void throwIfUserDoesntExist(Long userId){
        if(!userRepository.existsById(userId)){
            throw new UserNotFoundException(messageService.getMessage(ErrorMessages.USER_NOT_FOUND, userId));
        }
    }

    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(messageService.getMessage(ErrorMessages.USER_NOT_FOUND, userId)));
    }

    private void throwIfIdIsInvalid(Long userId, String message) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException(messageService.getMessage(message, userId));
        }
    }
}
