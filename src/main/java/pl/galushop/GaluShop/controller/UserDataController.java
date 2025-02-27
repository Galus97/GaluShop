package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
public class UserDataController {
    private final UserDataService userDataService;

    @GetMapping("/{id}")
    public ResponseEntity<UserData> showUserData(@PathVariable Long id){
        return ResponseEntity.ok(userDataService.showUserDataByUserId(id));
    }

    @PostMapping
    public ResponseEntity<UserData> saveUserData(@RequestBody UserDataRequest userDataRequest){
        userDataService.saveUserData(userDataRequest);
        return ResponseEntity.ok(userDataService.showUserData(userDataRequest.getUserDataId()));
    }

    @PutMapping
    public ResponseEntity<UserData> updateUserData(@RequestBody UserDataRequest userDataRequest){
        userDataService.updateUserData(userDataRequest);
        return ResponseEntity.ok(userDataService.showUserData(userDataRequest.getUserDataId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserData(@PathVariable Long id){
        userDataService.deleteUserData(id);
        return ResponseEntity.noContent().build();
    }
}
