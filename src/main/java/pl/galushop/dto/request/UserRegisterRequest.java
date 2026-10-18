package pl.galushop.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserRegisterRequest (
        @NotBlank
        @Length(min = 3)
        String firstName,

        @NotBlank
        @Length(min = 3)
        String lastName,

        @NotBlank
        @Length(min = 5)
        @Email
        String email,

        @NotBlank
        @Length(min = 5)
        String password)
        {

    }
