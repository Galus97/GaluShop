package pl.galushop.GaluShop.controller.userData;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.entity.UserData;
import pl.galushop.GaluShop.service.UserDataService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userData")
public class ShowUserDataController {
    private final UserDataService userDataService;

    @GetMapping("/show/{id}")
    public ResponseEntity<UserData> showUserData(@PathVariable Long id){
        return ResponseEntity.ok(userDataService.showUserDataByUserId(id));
    }
}
