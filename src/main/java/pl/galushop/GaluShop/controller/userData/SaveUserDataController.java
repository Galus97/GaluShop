package pl.galushop.GaluShop.controller.userData;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.UserDataRequest;
import pl.galushop.GaluShop.service.UserDataService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userData")
public class SaveUserDataController {
    private final UserDataService userDataService;

    @PutMapping("/save")
    public String saveUserData(@RequestBody UserDataRequest userDataRequest){

        userDataService.saveUserData(userDataRequest);

        return "success";
    }
}
