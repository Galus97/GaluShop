package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.entity.UserData;
import pl.galushop.GaluShop.repository.UserDataRepository;

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
        if(userId != null && userId > 0 && userService.getUserById(userId).isPresent()){
            return userDataRepository.findByUser_UserId(userId);
        }
        throw new IllegalArgumentException();
    }
}
