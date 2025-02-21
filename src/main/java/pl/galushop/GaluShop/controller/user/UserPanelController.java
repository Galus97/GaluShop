package pl.galushop.GaluShop.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserPanelController {

    @GetMapping("/userPanel")
    public String showPanelPage() {
        return "userPanel";
    }
}
