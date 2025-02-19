package pl.galushop.GaluShop.controller.userData;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.service.UserDataService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userData")
public class UserDataController {
    private final UserDataService userDataService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserData(@PathVariable Long id){
        userDataService.deleteUserData(id);
        return ResponseEntity.noContent().build();
    }
}
