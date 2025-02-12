package pl.galushop.GaluShop.controller.userData;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.UserDataRequest;
import pl.galushop.GaluShop.entity.UserData;
import pl.galushop.GaluShop.service.UserDataService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userData")
public class UpdateUserDataController {
    private final UserDataService userDataService;

    @PutMapping("/update")
    public ResponseEntity<UserData> updateUserData(@RequestBody UserDataRequest userDataRequest){
        userDataService.updateUserData(userDataRequest);
        return ResponseEntity.ok(userDataService.showUserData(userDataRequest.getUserDataId()));
    }
}
