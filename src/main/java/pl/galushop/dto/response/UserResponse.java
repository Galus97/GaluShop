package pl.galushop.dto.response;

public record UserResponse(
        String firstName,
        String lastName,
        String email,
        boolean enabled) {
}
