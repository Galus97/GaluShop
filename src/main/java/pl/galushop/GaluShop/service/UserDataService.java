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

import java.util.NoSuchElementException;

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
        userData.setCity(userDataRequest.getCity());
        userData.setStreet(userDataRequest.getStreet());
        userData.setStreetNumber(userDataRequest.getStreetNumber());
        userData.setApartmentNumber(userDataRequest.getApartmentNumber());
        userData.setZipCode(userDataRequest.getZipCode());
        userData.setPhoneNumber(userDataRequest.getPhoneNumber());

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
        UserData existingUserData = userDataRepository.findById(userDataRequest.getUserDataId())
                        .orElseThrow(() -> new UserDataNotFoundException(messageService.getMessage("error.userDataNotFound", userDataRequest.getUserDataId())));

        existingUserData.setCity(userDataRequest.getCity());
        existingUserData.setStreet(userDataRequest.getStreet());
        existingUserData.setStreetNumber(userDataRequest.getStreetNumber());
        existingUserData.setApartmentNumber(userDataRequest.getApartmentNumber());
        existingUserData.setZipCode(userDataRequest.getZipCode());
        existingUserData.setPhoneNumber(userDataRequest.getPhoneNumber());

        userDataRepository.save(existingUserData);
    }

    public void deleteUserData(Long userDataId) {
        if (userDataId != null && userDataId > 0) {
            if (userDataRepository.existsById(userDataId)) {
                userDataRepository.deleteById(userDataId);
            } else {
                throw new NoSuchElementException("That User Data doesn't exist in database");
            }
        } else {
            throw new IllegalArgumentException("User Data Id is invalid");
        }
    }
}
