package pl.galushop.GaluShop.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/default")
    public String defaultAfterLogin(Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            return "redirect:/userPanel";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EMPLOYEE"))) {
            return "redirect:/employeePanel";
        }
        return "redirect:/";
    }
}
