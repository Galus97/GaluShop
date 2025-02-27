package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.UserDataRequest;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.entity.UserData;
import pl.galushop.GaluShop.exception.UserDataNotFoundException;
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
     * @throws IllegalArgumentException if the request is null
     * @throws UsernameNotFoundException if the user does not exist
     */
    public void saveUserData(UserDataRequest userDataRequest) {
        if(userDataRequest == null){
            throw new IllegalArgumentException();
        }
        User user = userRepository.findById(userDataRequest.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException(messageService.getMessage("error.userNotFound", userDataRequest.getUserId())));

        UserData userData = new UserData();
        userData.setUser(user);
        setUserDataFields(userDataRequest, userData);

        userDataRepository.save(userData);
    }

    /**
     * Retrieves user data by its ID.
     *
     * @param userDataId the ID of the user data
     * @return the found user data entity
     * @throws IllegalArgumentException if the ID is null or invalid
     * @throws UserDataNotFoundException if user data is not found
     */
    public UserData getUserData(Long userDataId) {
        if(userDataId == null || userDataId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidUserDataId", userDataId));
        }
        return userDataRepository.findById(userDataId)
                .orElseThrow(() -> new UserDataNotFoundException(messageService.getMessage("error.userDataNotFound", userDataId)));
    }

    /**
     * Retrieves user data by the associated user ID.
     *
     * @param userId the ID of the user
     * @return the found user data entity
     * @throws IllegalArgumentException if the user ID is null or invalid
     * @throws UserDataNotFoundException if no user data is found for the user
     */
    public UserData getUserDataByUserId(Long userId) {
        if(userId == null || userId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidUserId", userId));
        }
        return userDataRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new UserDataNotFoundException(messageService.getMessage("error.userDataNotFound", userId)));
    }

    /**
     * Updates existing user data with the provided request details.
     *
     * @param userDataRequest the user data request containing updated details
     * @throws IllegalArgumentException if the user ID is null or invalid
     * @throws UserDataNotFoundException if the user data is not found
     */
    @Transactional
    public void updateUserData(UserDataRequest userDataRequest) {
        if(userDataRequest.getUserId() == null || userDataRequest.getUserId() < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidUserDataId", userDataRequest.getUserDataId()));
        }
        UserData existingUserData = userDataRepository.findById(userDataRequest.getUserDataId())
                        .orElseThrow(() -> new UserDataNotFoundException(messageService.getMessage("error.userDataNotFound", userDataRequest.getUserDataId())));

        setUserDataFields(userDataRequest, existingUserData);

        userDataRepository.save(existingUserData);
    }

    /**
     * Deletes user data by its ID.
     *
     * @param userDataId the ID of the user data
     * @throws IllegalArgumentException if the ID is null or invalid
     * @throws UserDataNotFoundException if the user data is not found
     */
    public void deleteUserData(Long userDataId) {
        if(userDataId == null || userDataId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidUserDataId", userDataId));
        }
        UserData userData = userDataRepository.findById(userDataId)
                .orElseThrow(() -> new UserDataNotFoundException(messageService.getMessage("error.userDataNotFound", userDataId)));
        userDataRepository.delete(userData);
    }

    /**
     * Helper method to set fields of a UserData entity from a UserDataRequest.
     *
     * @param userDataRequest the request containing user data fields
     * @param userData the user data entity to update
     */
    private static void setUserDataFields(UserDataRequest userDataRequest, UserData userData) {
        userData.setCity(userDataRequest.getCity());
        userData.setStreet(userDataRequest.getStreet());
        userData.setStreetNumber(userDataRequest.getStreetNumber());
        userData.setApartmentNumber(userDataRequest.getApartmentNumber());
        userData.setZipCode(userDataRequest.getZipCode());
        userData.setPhoneNumber(userDataRequest.getPhoneNumber());
    }
}
