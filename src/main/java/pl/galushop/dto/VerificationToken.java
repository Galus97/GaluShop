package pl.galushop.dto;

import java.time.LocalDateTime;

public record VerificationToken(
        String code,
        LocalDateTime expiresAt) {

    public boolean isExpired() {
        return LocalDateTime.now().isBefore(expiresAt);
    }
}
