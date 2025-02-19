package pl.galushop.GaluShop.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.UserRequest;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.exception.ValidationException;
import pl.galushop.GaluShop.service.RegisterUserService;
import pl.galushop.GaluShop.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final RegisterUserService registerUserService;


    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody UserRequest userRequest){
        try {
            registerUserService.saveNewUser(userRequest);
            return ResponseEntity.ok(userService.getUser(userRequest.getUserId()));
        } catch (ValidationException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
