package pl.galushop.GaluShop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginUserController {
    @GetMapping("/login")
    public String loginGet() {
        return "loginUser";
    }
}
