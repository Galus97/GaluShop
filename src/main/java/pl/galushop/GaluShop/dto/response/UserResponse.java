package pl.galushop.GaluShop.dto.response;

import pl.galushop.GaluShop.model.User;

public record UserResponse(Long userId, String firstName, String lastName, String email,
                           boolean enabled, String emailCode) {

    public static UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.isEnabled(),
                user.getEmailCode()
        );
    }
}
