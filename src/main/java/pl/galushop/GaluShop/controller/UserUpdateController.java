package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.UserRequest;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserUpdateController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/updateUser")
    public String updateUserFields(@RequestBody UserRequest userRequest){
        if (userRequest.getUserId() == null) {
            return "User ID is required";
        }

        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getFirstName()));

        userService.updateUser(userRequest.getUserId(), user);

        return "Success";
    }
}
