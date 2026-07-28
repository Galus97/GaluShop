package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.component.ErrorMessages;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.request.UserDataRequest;
import pl.galushop.GaluShop.dto.response.UserDataResponse;
import pl.galushop.GaluShop.model.User;
import pl.galushop.GaluShop.model.UserData;
import pl.galushop.GaluShop.exception.UserDataNotFoundException;
import pl.galushop.GaluShop.exception.UserNotFoundException;
import pl.galushop.GaluShop.repository.UserDataRepository;
import pl.galushop.GaluShop.util.ServiceValidator;

/**
 * Service class responsible for managing user data operations,
 * including saving, retrieving, updating and deleting user data.
 */
@Service
@RequiredArgsConstructor
public class UserDataService {
    private final UserDataRepository userDataRepository;
    private final UserService userService;
    private final MessageService messageService;
    private final ServiceValidator serviceValidator;

    /**
     * Retrieves user data by its unique ID.
     *
     * @param userDataId The ID of the user data.
     * @return A response DTO with the user data.
     * @throws IllegalArgumentException  If the ID is null or invalid.
     * @throws UserDataNotFoundException If no user data is found with the given ID.
     */
    public UserDataResponse getUserDataResponse(Long userDataId) {
        serviceValidator.throwIfIdIsNotValid(userDataId, ErrorMessages.INVALID_USER_DATA_ID);
        return UserDataResponse.fromEntity(getUserDataOrThrow(userDataId, ErrorMessages.USER_DATA_NOT_FOUND));
    }

    /**
     * Retrieves user data using the user’s ID.
     *
     * @param userId The ID of the user whose data is being retrieved.
     * @return A response DTO with the user data.
     * @throws IllegalArgumentException  If the user ID is null or invalid.
     * @throws UserDataNotFoundException If no data is found for the given user.
     */
    public UserDataResponse getUserDataByUser(Long userId) {
        serviceValidator.throwIfIdIsNotValid(userId, ErrorMessages.INVALID_USER_ID);
        return UserDataResponse.fromEntity(getUserDataByUserOrThrow(userId));
    }

    /**
     * Saves new user data based on the provided request.
     *
     * @param userDataRequest The request containing user data details.
     * @return A response DTO with the saved user data.
     * @throws IllegalArgumentException If the request is null.
     * @throws UserNotFoundException    If the user associated with the data is not found.
     */
    @Transactional
    public UserDataResponse saveUserData(UserDataRequest userDataRequest) {
        serviceValidator.throwIfRequestIsNull(userDataRequest, ErrorMessages.INVALID_USER_DATA_REQUEST);
        return UserDataResponse.fromEntity(userDataRepository.save(buildUserData(userDataRequest)));
    }

    /**
     * Deletes user data by its ID.
     *
     * @param userDataId The ID of the user data to delete.
     * @throws IllegalArgumentException  If the ID is null or invalid.
     * @throws UserDataNotFoundException If no user data is found with the given ID.
     */
    public void deleteUserData(Long userDataId) {
        serviceValidator.throwIfIdIsNotValid(userDataId, ErrorMessages.INVALID_USER_DATA_ID);
        UserData userData = getUserDataOrThrow(userDataId, ErrorMessages.USER_DATA_NOT_FOUND);

        userDataRepository.delete(userData);
    }

    /**
     * Updates existing user data with new values provided in the request.
     *
     * @param userDataRequest The request containing updated user data details.
     * @return A response DTO with the updated user data.
     * @throws IllegalArgumentException  If the user ID is invalid.
     * @throws UserDataNotFoundException If the user data is not found.
     */
    @Transactional
    public UserDataResponse updateUserData(UserDataRequest userDataRequest) {
        serviceValidator.throwIfRequestIsNull(userDataRequest, ErrorMessages.INVALID_USER_DATA_REQUEST);
        serviceValidator.throwIfIdIsNotValid(userDataRequest.getUserDataId(), ErrorMessages.INVALID_USER_DATA_ID);

        UserData existingUserData = getUserDataOrThrow(userDataRequest.getUserDataId(), ErrorMessages.USER_DATA_NOT_FOUND);

        existingUserData.setCity(userDataRequest.getCity());
        existingUserData.setStreet(userDataRequest.getStreet());
        existingUserData.setStreetNumber(userDataRequest.getStreetNumber());
        existingUserData.setApartmentNumber(userDataRequest.getApartmentNumber());
        existingUserData.setZipCode(userDataRequest.getZipCode());
        existingUserData.setPhoneNumber(userDataRequest.getPhoneNumber());

        return UserDataResponse.fromEntity(userDataRepository.save(existingUserData));
    }

    /**
     * Constructs a UserData entity from a request DTO.
     *
     * @param userDataRequest The request containing data to build the entity.
     * @return A new UserData entity ready for persistence.
     * @throws UserNotFoundException If the user is not found.
     */
    private UserData buildUserData(UserDataRequest userDataRequest) {
        User user = userService.getUserEntity(userDataRequest.getUserId());

        return UserData.builder()
                .userDataId(null)
                .user(user)
                .city(userDataRequest.getCity())
                .street(userDataRequest.getStreet())
                .streetNumber(userDataRequest.getStreetNumber())
                .apartmentNumber(userDataRequest.getApartmentNumber())
                .zipCode(userDataRequest.getZipCode())
                .phoneNumber(userDataRequest.getPhoneNumber())
                .build();
    }

    /**
     * Retrieves user data or throws an exception if not found.
     *
     * @param userDataId The ID of the user data.
     * @param message    The error message key for the exception.
     * @return The found UserData entity.
     * @throws UserDataNotFoundException If no user data is found with the given ID.
     */
    private UserData getUserDataOrThrow(Long userDataId, String message) {
        return userDataRepository.findById(userDataId)
                .orElseThrow(() -> new UserDataNotFoundException(messageService.getMessage(message, userDataId)));
    }

    private UserData getUserDataByUserOrThrow(Long userId) {
        return userDataRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new UserDataNotFoundException(messageService.getMessage(
                        ErrorMessages.USER_DATA_NOT_FOUND_BY_USER_ID, userId)));
    }
}
