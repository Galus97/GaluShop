package pl.galushop.GaluShop.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.service.UserService;

@RestController
@RequiredArgsConstructor
public class DeleteUserController {
        private final UserService userService;
    @GetMapping("/deleteUser")
    public String deleteUser(@RequestBody Long userId){
        if(userId == null || userId <= 0){
            return "User ID is not valid";
        }
        return userService.getUserById(userId)
                .map(user -> {
                    userService.deleteUserFromDatabase(user);
                    return "User has been deleted";
                })
                .orElse("User not found");
    }
}
