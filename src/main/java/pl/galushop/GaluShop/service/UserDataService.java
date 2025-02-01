package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.entity.UserData;
import pl.galushop.GaluShop.repository.UserDataRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserDataService {
    private final UserDataRepository userDataRepository;
    private final UserService userService;

    public void saveUserDataToDatabase(UserData userData) {
        if (userData != null) {
            userDataRepository.save(userData);
        }
    }

    public UserData showUserData(Long userId){
        if(userId != null && userId > 0){
            if(userService.getUserById(userId).isPresent()){
                return userDataRepository.findByUser_UserId(userId);
            }
            throw new NoSuchElementException("That User Data doesn't exist in database");
        }
        throw new IllegalArgumentException("User Id is invalid");
    }

    public void updateUserData(Long userDataId, String city, String street, Integer streetNumber,
                               Integer apartmentNumber, String zipCode, Integer phoneNumber){
        if(userDataId != null && userDataId > 0){
            if(userDataRepository.existsById(userDataId)){
                userDataRepository.updateByUserDataId(userDataId, city, street, streetNumber,
                        apartmentNumber, zipCode, phoneNumber);
            } else {
                throw new NoSuchElementException("That User Data doesn't exist in database");
            }
        } else {
            throw new IllegalArgumentException("User Data Id is invalid");
        }
    }
}
