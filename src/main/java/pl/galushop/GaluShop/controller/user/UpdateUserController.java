package pl.galushop.GaluShop.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.UserRequest;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UpdateUserController {
    private final UserService userService;

    @PutMapping("/update")
    public ResponseEntity<User> updateUserFields(@RequestBody UserRequest userRequest){
        userService.updateUser(userRequest);
        return ResponseEntity.ok(userService.getUser(userRequest.getUserId()));
    }
}
