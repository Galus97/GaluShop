package pl.galushop.GaluShop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.exception.ValidationException;
import pl.galushop.GaluShop.service.RegisterUserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RegisterUserController {
    private final RegisterUserService registerUserService;

    @GetMapping("/registerUser")
    public String showRegisterPage(Model model){
        model.addAttribute("user", new User());
        return "registerUser";
    }

    @PostMapping("/registerUser")
    public String saveNewUser(@Valid User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println("(bindingResult.hasErrors() -- " + bindingResult.getAllErrors());
            return "registerUser";
        }

        try {
            registerUserService.saveNewUserToDatabase(user);
        } catch (ValidationException exception) {
            List<String> errors = exception.getValidationErrors();
            bindingResult.rejectValue("email", "", errors.get(0));
        }

        return "redirect:login";
    }
}
