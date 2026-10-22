package pl.galushop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.galushop.component.Role;
import pl.galushop.dto.request.UserLoginRequest;
import pl.galushop.dto.request.UserRequest;
import pl.galushop.dto.response.UserLoginResponse;
import pl.galushop.dto.response.UserResponse;
import pl.galushop.exception.ValidationException;
import pl.galushop.service.AuthService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/registration/user")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRequest userRequest) {
        try {
            UserResponse savedUser = authService.saveNewUser(userRequest, Role.ROLE_USER);
            return ResponseEntity.created(URI.create("/user/" + savedUser.userId()))
                    .body(savedUser);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getValidationErrors());
        }
    }

    @PostMapping("/registration/employee")
    public ResponseEntity<?> registerEmployee(@RequestBody @Valid UserRequest userRequest) {
        try {
            UserResponse savedUser = authService.saveNewUser(userRequest, Role.ROLE_EMPLOYEE);
            return ResponseEntity.created(URI.create("/user/" + savedUser.userId()))
                    .body(savedUser);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getValidationErrors());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> loginUser(@RequestBody @Valid UserLoginRequest request) {
        return ResponseEntity.ok(new UserLoginResponse("")); // TODO
    }
}
