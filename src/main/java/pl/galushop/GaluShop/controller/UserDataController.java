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
import pl.galushop.GaluShop.dto.request.UserDataRequest;
import pl.galushop.GaluShop.dto.response.UserDataResponse;
import pl.galushop.GaluShop.service.UserDataService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userData")
public class UserDataController {
    private final UserDataService userDataService;

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDataResponse> showUserDataByUserId(@PathVariable Long id){
        return ResponseEntity.ok(userDataService.getUserDataByUserId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDataResponse> showUserData(@PathVariable Long id){
        return ResponseEntity.ok(userDataService.getUserDataResponse(id));
    }

    @PostMapping
    public ResponseEntity<UserDataResponse> saveUserData(@RequestBody UserDataRequest userDataRequest){
        UserDataResponse savedUserData = userDataService.saveUserData(userDataRequest);
        return ResponseEntity.created(URI.create("/userData/" + savedUserData.userDataId()))
                .body(savedUserData);
    }

    @PutMapping
    public ResponseEntity<UserDataResponse> updateUserData(@RequestBody UserDataRequest userDataRequest){
        return ResponseEntity.ok(userDataService.updateUserData(userDataRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserData(@PathVariable Long id){
        userDataService.deleteUserData(id);
        return ResponseEntity.noContent().build();
    }
}
