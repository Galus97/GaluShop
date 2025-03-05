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
}