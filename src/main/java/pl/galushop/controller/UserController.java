package pl.galushop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.dto.response.UserResponse;
import pl.galushop.model.User;
import pl.galushop.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<UserResponse> showUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.getUserResponse(user));
    }
}
