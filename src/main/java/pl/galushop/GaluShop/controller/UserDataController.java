package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.UserDataRequest;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.entity.UserData;
import pl.galushop.GaluShop.service.UserDataService;
import pl.galushop.GaluShop.service.UserService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserDataController {
    private final UserService userService;
    private final UserDataService userDataService;

    @PostMapping("/userData")
    public String saveUserData(@RequestBody UserDataRequest userDataRequest){

        Optional<User> user = userService.getUserById(userDataRequest.getUserId());
        if(user.isEmpty()){
            return "User not found";
        }

        UserData userData = new UserData();
        userData.setUser(user.get());
        userData.setCity(userDataRequest.getCity());
        userData.setStreet(userDataRequest.getStreet());
        userData.setStreetNumber(userDataRequest.getStreetNumber());
        userData.setApartmentNumber(userDataRequest.getApartmentNumber());
        userData.setZipCode(userDataRequest.getZipCode());
        userData.setPhoneNumber(userDataRequest.getPhoneNumber());

        userDataService.saveUserDataToDatabase(userData);

        return "success";
    }
}
