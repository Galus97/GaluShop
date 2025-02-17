package pl.galushop.GaluShop.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.galushop.GaluShop.dto.UserRequest;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.exception.ValidationException;
import pl.galushop.GaluShop.service.EmailService;
import pl.galushop.GaluShop.service.RegisterUserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class RegisterUserController {
    private final RegisterUserService registerUserService;

    //Dokończyć
    @PostMapping("/register")
    public String saveUser(@RequestBody UserRequest userRequest){

    }
}
