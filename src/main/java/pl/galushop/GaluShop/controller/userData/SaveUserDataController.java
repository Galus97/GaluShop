package pl.galushop.GaluShop.controller.userData;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.UserDataRequest;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.entity.UserData;
import pl.galushop.GaluShop.service.UserDataService;
import pl.galushop.GaluShop.service.UserService;

@RestController
@RequiredArgsConstructor
public class SaveUserDataController {
    private final UserService userService;
    private final UserDataService userDataService;

    @PostMapping("/userData")
    public String saveUserData(@RequestBody UserDataRequest userDataRequest){

        User user = userService.getUser(userDataRequest.getUserId());

        UserData userData = new UserData();
        userData.setUser(user);
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
