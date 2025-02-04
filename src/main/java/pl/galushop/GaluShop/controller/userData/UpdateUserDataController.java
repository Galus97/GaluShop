package pl.galushop.GaluShop.controller.userData;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.UserDataRequest;
import pl.galushop.GaluShop.entity.UserData;
import pl.galushop.GaluShop.service.UserDataService;

@RestController
@RequiredArgsConstructor
public class UpdateUserDataController {
    private final UserDataService userDataService;

    @GetMapping("/userData/update")
    public UserData updateUserData(@RequestBody UserDataRequest userDataRequest){
        userDataService.updateUserData(
                userDataRequest.getUserDataId(),
                userDataRequest.getCity(),
                userDataRequest.getStreet(),
                userDataRequest.getStreetNumber(),
                userDataRequest.getApartmentNumber(),
                userDataRequest.getZipCode(),
                userDataRequest.getPhoneNumber()
        );

        return userDataService.showUserData(userDataRequest.getUserDataId());
    }
}
