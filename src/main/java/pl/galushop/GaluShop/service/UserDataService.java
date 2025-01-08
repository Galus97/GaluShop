package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.entity.UserData;
import pl.galushop.GaluShop.repository.UserDataRepository;

@Service
@RequiredArgsConstructor
public class UserDataService {
    private final UserDataRepository userDataRepository;

    public void saveUserDataToDatabase(UserData userData) {
        if (userData != null) {
            userDataRepository.save(userData);
        }
    }
}
