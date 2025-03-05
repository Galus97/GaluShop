package pl.galushop.GaluShop.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserDataTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldCreateValidUserData() {
        UserData userData = UserData.builder()
                .city("Warsaw")
                .street("Main Street")
                .streetNumber(10)
                .apartmentNumber(5)
                .zipCode("00-123")
                .phoneNumber(123456789)
                .build();

        Set<ConstraintViolation<UserData>> violations = validator.validate(userData);
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldDetectInvalidCity() {
        UserData userData = UserData.builder()
                .city("A") // Too short
                .street("Main Street")
                .streetNumber(10)
                .apartmentNumber(5)
                .zipCode("00-123")
                .phoneNumber(123456789)
                .build();

        Set<ConstraintViolation<UserData>> violations = validator.validate(userData);

        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").contains("size must be between 3 and 2147483647");
    }

    @Test
    void shouldDetectInvalidStreet() {
        UserData userData = UserData.builder()
                .city("Warsaw")
                .street("A") // Too short
                .streetNumber(10)
                .apartmentNumber(5)
                .zipCode("00-123")
                .phoneNumber(123456789)
                .build();

        Set<ConstraintViolation<UserData>> violations = validator.validate(userData);

        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").contains("size must be between 3 and 2147483647");
    }

    @Test
    void shouldDetectInvalidStreetNumber() {
        UserData userData = UserData.builder()
                .city("Warsaw")
                .street("Main Street")
                .streetNumber(null) // null street number
                .apartmentNumber(5)
                .zipCode("00-123")
                .phoneNumber(123456789)
                .build();

        Set<ConstraintViolation<UserData>> violations = validator.validate(userData);

        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").contains("must not be null");
    }

    @Test
    void shouldDetectInvalidZipCode() {
        UserData userData = UserData.builder()
                .city("Warsaw")
                .street("Main Street")
                .streetNumber(10)
                .apartmentNumber(5)
                .zipCode("") // blank
                .phoneNumber(123456789)
                .build();

        Set<ConstraintViolation<UserData>> violations = validator.validate(userData);

        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").contains("must not be blank");
    }

    @Test
    void shouldDetectInvalidPhoneNumber() {
        UserData userData = UserData.builder()
                .city("Warsaw")
                .street("Main Street")
                .streetNumber(10)
                .apartmentNumber(5)
                .zipCode("00-123")
                .phoneNumber(null) // null phone number
                .build();

        Set<ConstraintViolation<UserData>> violations = validator.validate(userData);
        assertThat(violations).isNotEmpty();
    }
}