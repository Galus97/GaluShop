package pl.galushop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.dto.request.UserRequest;
import pl.galushop.dto.response.UserResponse;
import pl.galushop.exception.ValidationException;
import pl.galushop.service.UserService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserAuthController {
    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRequest userRequest) {
        try {
            UserResponse savedUser = userService.saveNewUser(userRequest);
            return ResponseEntity.created(URI.create("/user/" + savedUser.userId()))
                    .body(savedUser);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getValidationErrors());
        }
    }
}
