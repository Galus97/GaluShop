package pl.galushop.mapper;

import pl.galushop.dto.response.UserResponse;
import pl.galushop.model.User;

public class UserMapper {

    public static UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.isEnabled()
        );
    }
}
