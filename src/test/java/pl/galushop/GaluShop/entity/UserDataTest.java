package pl.galushop.GaluShop.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserDataTest {
    private static Validator validator;
    private UserData userData;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
     void init(){
        userData = UserData.builder()
                .city("Warsaw")
                .street("Main Street")
                .streetNumber(10)
                .apartmentNumber(5)
                .zipCode("00-123")
                .phoneNumber(123456789)
                .build();
    }

    @Test
    void shouldCreateValidUserData() {
        Set<ConstraintViolation<UserData>> violations = validator.validate(userData);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldDetectInvalidCity() {
        userData.setCity("A"); // Too short

        Set<ConstraintViolation<UserData>> violations = validator.validate(userData);

        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").contains("size must be between 3 and 2147483647");
    }

    @Test
    void shouldDetectInvalidStreet() {
        userData.setStreet("A"); // Too short

        Set<ConstraintViolation<UserData>> violations = validator.validate(userData);

        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").contains("size must be between 3 and 2147483647");
    }

    @Test
    void shouldDetectInvalidStreetNumber() {
        userData.setStreetNumber(null);

        Set<ConstraintViolation<UserData>> violations = validator.validate(userData);

        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").contains("must not be null");
    }

    @Test
    void shouldDetectInvalidApartmentNumber() {
        userData.setApartmentNumber(null);

        Set<ConstraintViolation<UserData>> violations = validator.validate(userData);

        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").contains("must not be null");
    }

    @Test
    void shouldDetectInvalidZipCode() {
        userData.setZipCode(""); // blank

        Set<ConstraintViolation<UserData>> violations = validator.validate(userData);

        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").contains("must not be blank");
    }

    @Test
    void shouldDetectInvalidPhoneNumber() {
        userData.setPhoneNumber(null);

        Set<ConstraintViolation<UserData>> violations = validator.validate(userData);
        assertThat(violations).isNotEmpty();
    }
}