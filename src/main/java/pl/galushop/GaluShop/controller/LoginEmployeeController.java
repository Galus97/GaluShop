package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LoginEmployeeController {

    @GetMapping("/loginEmployee")
    public String showLoginEmployeePage(){
        return "loginEmployee";
    }
}
