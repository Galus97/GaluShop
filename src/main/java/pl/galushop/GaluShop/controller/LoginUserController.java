package pl.galushop.GaluShop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginUserController {
    @GetMapping("/loginUser")
    public String showLoginPage() {
        return "loginUser";
    }
}
