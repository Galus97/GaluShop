package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.galushop.GaluShop.entity.User;

@Controller
@RequiredArgsConstructor
public class RegisterUserController {

    @GetMapping("/registerUser")
    public String showRegisterPage(Model model){
        model.addAttribute("user", new User());
        return "registerUser";
    }
}
