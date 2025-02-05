package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.entity.UserData;
import pl.galushop.GaluShop.repository.UserDataRepository;
import pl.galushop.GaluShop.repository.UserRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserDataService {
    private final UserDataRepository userDataRepository;
    private final UserRepository userRepository;

    public void saveUserDataToDatabase(UserData userData) {
        if (userData != null) {
            userDataRepository.save(userData);
        }
    }

    public UserData showUserData(Long userDataId) {
        if (userDataId != null && userDataId > 0) {
            if (userDataRepository.findById(userDataId).isPresent()) {
                return userDataRepository.findById(userDataId).get();
            }
            throw new NoSuchElementException("That User Data doesn't exist in database");
        }
        throw new IllegalArgumentException("User Data Id is invalid");
    }

    public UserData showUserDataByUserId(Long userId) {
        if (userId != null && userId > 0) {
            if (userRepository.findByUserId(userId).isPresent()) {
                return userDataRepository.findByUser_UserId(userId);
            }
            throw new NoSuchElementException("That User Data doesn't exist in database");
        }
        throw new IllegalArgumentException("User Id is invalid");
    }

    public void updateUserData(Long userDataId, String city, String street, Integer streetNumber,
                               Integer apartmentNumber, String zipCode, Integer phoneNumber) {
        if (userDataId != null && userDataId > 0) {
            if (userDataRepository.existsById(userDataId)) {
                userDataRepository.updateByUserDataId(userDataId, city, street, streetNumber,
                        apartmentNumber, zipCode, phoneNumber);
            } else {
                throw new NoSuchElementException("That User Data doesn't exist in database");
            }
        } else {
            throw new IllegalArgumentException("User Data Id is invalid");
        }
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
