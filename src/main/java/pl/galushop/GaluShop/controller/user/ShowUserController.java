package pl.galushop.GaluShop.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.UserRequest;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class ShowUserController {
    private final UserService userService;

    @GetMapping("/show")
    public User showUserInfo(@RequestBody UserRequest userRequest){
       return userService.getUser(userRequest.getUserId());
    }
}

