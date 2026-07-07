package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
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
import pl.galushop.GaluShop.util.ServiceValidator;

/**
 * Service class responsible for managing user-related operations,
 * such as retrieving, updating, and deleting users.
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageService messageService;
    private final ServiceValidator serviceValidator;
    /**
     * Retrieves a user entity by its ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The User entity.
     * @throws IllegalArgumentException If the ID is null or invalid.
     * @throws UserNotFoundException    If the user is not found.
     */
    public User getUserEntity(Long userId) {
        serviceValidator.throwIfIdIsNotValid(userId, ErrorMessages.INVALID_USER_ID);
        return getUserOrThrowIfNotExist(userId);
    }

    /**
     * Retrieves a user's data as a response DTO by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return A UserResponse DTO containing user details.
     * @throws IllegalArgumentException If the ID is null or invalid.
     * @throws UserNotFoundException    If the user is not found.
     */
    public UserResponse getUserResponse(Long userId) {
        serviceValidator.throwIfIdIsNotValid(userId, ErrorMessages.INVALID_USER_ID);
        return UserResponse.fromEntity(getUserOrThrowIfNotExist(userId));
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userId The ID of the user to delete.
     * @throws IllegalArgumentException If the ID is null or invalid.
     * @throws UserNotFoundException    If the user is not found.
     */
    public void deleteUser(Long userId) {
        serviceValidator.throwIfIdIsNotValid(userId, ErrorMessages.INVALID_USER_ID);
        userRepository.delete(getUserOrThrowIfNotExist(userId));
    }

    /**
     * Updates user details based on the given request.
     * If the password is provided, it is updated and encoded;
     * otherwise, the existing password remains unchanged.
     *
     * @param userRequest The request containing updated user data.
     * @return A UserResponse with the updated user information.
     * @throws IllegalArgumentException If the user ID is invalid.
     * @throws UserNotFoundException    If the user is not found.
     */
    @Transactional
    public UserResponse updateUser(UserRequest userRequest) {
        serviceValidator.throwIfRequestIsNull(userRequest, ErrorMessages.INVALID_USER_REQUEST);
        serviceValidator.throwIfIdIsNotValid(userRequest.getUserId(), ErrorMessages.INVALID_USER_ID);

        User existingUser = getUserOrThrowIfNotExist(userRequest.getUserId());

        existingUser.setFirstName(userRequest.getFirstName());
        existingUser.setLastName(userRequest.getLastName());
        existingUser.setEmail(userRequest.getEmail());

        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        return UserResponse.fromEntity(userRepository.save(existingUser));
    }

    /**
     * Retrieves a user by ID or throws an exception if not found.
     *
     * @param userId The ID of the user.
     * @return The User entity.
     * @throws UserNotFoundException If the user is not found.
     */
    private User getUserOrThrowIfNotExist(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(messageService.getMessage(ErrorMessages.USER_NOT_FOUND, userId)));
    }
}
