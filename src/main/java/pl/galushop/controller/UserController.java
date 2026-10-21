package pl.galushop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.galushop.dto.request.UserRequest;
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

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(
            @RequestBody @Valid UserRequest userRequest,
            @AuthenticationPrincipal User user
            ) {
        return ResponseEntity.ok(userService.updateUser(userRequest, user));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal User user) {
        userService.deleteUser(user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/enable")
    public ResponseEntity<UserResponse> enableUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.enableUser(user));
    }
}
