package pl.galushop.GaluShop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.galushop.GaluShop.dto.UserRequest;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.exception.ValidationException;
import pl.galushop.GaluShop.service.RegisterUserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RegisterUserController {
    private final RegisterUserService registerUserService;

    @GetMapping("/registerUser")
    public String showRegisterUserPage(Model model) {
        model.addAttribute("user", new User());
        return "registerUser";
    }


    @PostMapping("/registerUser")
    public String saveNewUser(@RequestBody UserRequest userRequest){

        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        try{
            registerUserService.saveNewUser(user);
        } catch (ValidationException exception){
            List<String> errors = exception.getValidationErrors();
            for (String error : errors) {
                System.out.println(error);
            }
            return "faild";
        }
        return "succes";
    }
}
