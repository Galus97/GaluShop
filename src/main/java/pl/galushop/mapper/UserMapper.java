package pl.galushop.mapper;

import pl.galushop.dto.request.UserRequest;
import pl.galushop.dto.response.UserResponse;
import pl.galushop.model.User;

import java.util.HashSet;

public class UserMapper {

    public static UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.isEnabled()
        );
    }

    public static User toUserModelFromRegister(UserRequest request) {
        return User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(request.password())
                .enabled(false)
                .roles(new HashSet<>())
                .build();
    }
}
