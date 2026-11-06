package pl.galushop.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

public record UserLoginRequest(
        @NotBlank
        @Length(min = 5)
        @Email
        @Getter
        String email,

        @NotBlank
        @Length(min = 5)
        @Getter
        String password) {
}
