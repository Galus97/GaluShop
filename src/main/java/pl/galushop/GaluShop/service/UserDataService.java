package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.UserDataRequest;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.entity.UserData;
import pl.galushop.GaluShop.exception.UserDataNotFoundException;
import pl.galushop.GaluShop.repository.UserDataRepository;
import pl.galushop.GaluShop.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDataService {
    private final UserDataRepository userDataRepository;
    private final UserRepository userRepository;
    private final MessageService messageService;

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

    public UserData showUserData(Long userDataId) {
        if(userDataId == null || userDataId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidUserDataId", userDataId));
        }
        return userDataRepository.findById(userDataId)
                .orElseThrow(() -> new UserDataNotFoundException(messageService.getMessage("error.userDataNotFound", userDataId)));
    }

    public UserData showUserDataByUserId(Long userId) {
        if(userId == null || userId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidUserId", userId));
        }
        return userDataRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new UserDataNotFoundException(messageService.getMessage("error.userDataNotFound", userId)));
    }

    public void updateUserData(UserDataRequest userDataRequest) {
        if(userDataRequest.getUserId() == null || userDataRequest.getUserId() < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidUserDataId", userDataRequest.getUserDataId()));
        }
        UserData existingUserData = userDataRepository.findById(userDataRequest.getUserDataId())
                        .orElseThrow(() -> new UserDataNotFoundException(messageService.getMessage("error.userDataNotFound", userDataRequest.getUserDataId())));

        setUserDataFields(userDataRequest, existingUserData);

        userDataRepository.save(existingUserData);
    }


    public void deleteUserData(Long userDataId) {
        if(userDataId == null || userDataId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidUserDataId", userDataId));
        }
        UserData userData = userDataRepository.findById(userDataId)
                .orElseThrow(() -> new UserDataNotFoundException(messageService.getMessage("error.userDataNotFound", userDataId)));
        userDataRepository.delete(userData);
    }

    private static void setUserDataFields(UserDataRequest userDataRequest, UserData userData) {
        userData.setCity(userDataRequest.getCity());
        userData.setStreet(userDataRequest.getStreet());
        userData.setStreetNumber(userDataRequest.getStreetNumber());
        userData.setApartmentNumber(userDataRequest.getApartmentNumber());
        userData.setZipCode(userDataRequest.getZipCode());
        userData.setPhoneNumber(userDataRequest.getPhoneNumber());
    }
}
