package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.component.ErrorMessages;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.request.UserDataRequest;
import pl.galushop.GaluShop.dto.response.UserDataResponse;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.entity.UserData;
import pl.galushop.GaluShop.exception.UserDataNotFoundException;
import pl.galushop.GaluShop.exception.UserNotFoundException;
import pl.galushop.GaluShop.repository.UserDataRepository;
import pl.galushop.GaluShop.repository.UserRepository;

/**
 * Service class responsible for managing user data operations.
 */
@Service
@RequiredArgsConstructor
public class UserDataService {
    private final UserDataRepository userDataRepository;
    private final UserRepository userRepository;
    private final MessageService messageService;

    /**
     * Saves new user data based on the provided request.
     *
     * @param userDataRequest the user data request containing user details
     * @return The created UserData
     * @throws IllegalArgumentException  if the request is null
     * @throws UsernameNotFoundException if the user does not exist
     */
    @Transactional
    public UserDataResponse saveUserData(UserDataRequest userDataRequest) {
        throwIfRequestIsInvalid(userDataRequest);
        return UserDataResponse.fromEntity(userDataRepository.save(buildUserData(userDataRequest)));
    }

    /**
     * Retrieves user data by its ID.
     *
     * @param userDataId the ID of the user data.
     * @return the found user data entity.
     * @throws IllegalArgumentException  if the ID is null or negative.
     * @throws UserDataNotFoundException if user data is not found.
     */
    public UserDataResponse getUserDataResponse(Long userDataId) {
        throwIfIdIsInvalid(userDataId, ErrorMessages.INVALID_USER_DATA_ID);
        return UserDataResponse.fromEntity(getUserDataOrThrow(userDataId, ErrorMessages.USER_DATA_NOT_FOUND));
    }

    /**
     * Retrieves user data by the associated user ID.
     *
     * @param userId the ID of the user.
     * @return the found user data entity.
     * @throws IllegalArgumentException  if the user ID is null or negative.
     * @throws UserDataNotFoundException if no user data is found for the user.
     */
    public UserDataResponse getUserDataByUserId(Long userId) {
        throwIfIdIsInvalid(userId, ErrorMessages.INVALID_USER_ID);

        return UserDataResponse.fromEntity(getUserDataOrThrow(userId, ErrorMessages.USER_DATA_NOT_FOUND));
    }

    /**
     * Deletes user data by its ID.
     *
     * @param userDataId the ID of the user data.
     * @throws IllegalArgumentException  if the ID is null or negative.
     * @throws UserDataNotFoundException if the user data is not found.
     */
    public void deleteUserData(Long userDataId) {
        throwIfIdIsInvalid(userDataId, ErrorMessages.INVALID_USER_DATA_ID);
        UserData userData = getUserDataOrThrow(userDataId, ErrorMessages.USER_DATA_NOT_FOUND);

        userDataRepository.delete(userData);
    }

    /**
     * Updates existing user data with the provided request details.
     *
     * @param userDataRequest the user data request containing updated details.
     * @throws IllegalArgumentException  if the user data ID is null or invalid.
     * @throws UserDataNotFoundException if the user data is not found.
     */
    @Transactional
    public UserDataResponse updateUserData(UserDataRequest userDataRequest) {
        throwIfIdIsInvalid(userDataRequest.getUserId(), ErrorMessages.INVALID_USER_DATA_ID);

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
     * Builds a UserData entity from the given request.
     *
     * @param userDataRequest the request containing user details.
     * @return a new UserData instance.
     * @throws IllegalArgumentException  if the request is null.
     * @throws UsernameNotFoundException if the user is not found.
     */
    private UserData buildUserData(UserDataRequest userDataRequest) {
        User user = userRepository.findById(userDataRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException(messageService.getMessage(
                        ErrorMessages.USER_DATA_NOT_FOUND, userDataRequest.getUserId())));

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

    private void throwIfRequestIsInvalid(UserDataRequest userDataRequest) {
        if (userDataRequest == null) {
            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.INVALID_USER_DATA_REQUEST));
        }
    }

    private void throwIfIdIsInvalid(Long id, String message) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(messageService.getMessage(message, id));
        }
    }

    private UserData getUserDataOrThrow(Long userDataId, String message) {
        return userDataRepository.findById(userDataId)
                .orElseThrow(() -> new UserDataNotFoundException(messageService.getMessage(message, userDataId)));
    }
}
