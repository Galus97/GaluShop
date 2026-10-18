package pl.galushop.dto.response;

public record UserResponse(
        Long userId,
        String firstName,
        String lastName,
        String email,
        boolean enabled) {
}
