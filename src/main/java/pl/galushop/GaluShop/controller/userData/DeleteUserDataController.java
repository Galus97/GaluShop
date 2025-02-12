package pl.galushop.GaluShop.controller.userData;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.UserDataRequest;
import pl.galushop.GaluShop.service.UserDataService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userData")
public class DeleteUserDataController {
    private final UserDataService userDataService;

    @DeleteMapping("/delete")
    public String deleteUserData(@RequestBody UserDataRequest userDataRequest){
        userDataService.deleteUserData(userDataRequest.getUserDataId());
        return "Success";
    }
}
