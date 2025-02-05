package pl.galushop.GaluShop.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.UserRequest;
import pl.galushop.GaluShop.service.UserService;

@RestController
@RequiredArgsConstructor
public class DeleteUserController {
        private final UserService userService;
    @GetMapping("/deleteUser")
    public String deleteUser(@RequestBody UserRequest userRequest){
        userService.getUserById(userRequest.getUserId());
        return "Success";
    }
}
