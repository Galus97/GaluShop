package pl.galushop.GaluShop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeePanelController {

    @GetMapping("/employeePanel")
    public String showPanelPage(){
        return "employeePanel";
    }
}
