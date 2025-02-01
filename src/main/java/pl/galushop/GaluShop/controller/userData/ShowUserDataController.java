package pl.galushop.GaluShop.controller.userData;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.entity.UserData;
import pl.galushop.GaluShop.service.UserDataService;

@RestController
@RequiredArgsConstructor
public class ShowUserDataController {
    private final UserDataService userDataService;

    @GetMapping("/userData/show")
    public UserData showUserData(){
        return userDataService.showUserData(1L);
    }
}
