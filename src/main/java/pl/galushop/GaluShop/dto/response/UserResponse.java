package pl.galushop.GaluShop.dto.response;

public record UserResponse(Long userId, String firstName, String lastName, String email,
                            String password, boolean enabled, String emailCode) {
}
