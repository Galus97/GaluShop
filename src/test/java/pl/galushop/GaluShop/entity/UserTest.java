package pl.galushop.GaluShop.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldCreateUserSuccessfully() {
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("securePassword")
                .enabled(true)
                .emailCode("123456")
                .build();

        assertThat(user).isNotNull();
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(user.getPassword()).isEqualTo("securePassword");
        assertThat(user.isEnabled()).isTrue();
        assertThat(user.getEmailCode()).isEqualTo("123456");
    }

    @Test
    void shouldDetectInvalidEmail() {
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("invalid-email")
                .password("securePassword")
                .enabled(true)
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getMessage().contains("must be a well-formed email address"));
    }

    @Test
    void shouldDetectShortFirstName() {
        User user = User.builder()
                .firstName("Jo") // too short
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("securePassword")
                .enabled(true)
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getMessage().contains("size must be between 3 and"));
    }
}