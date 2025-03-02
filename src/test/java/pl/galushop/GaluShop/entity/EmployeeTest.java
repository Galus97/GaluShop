package pl.galushop.GaluShop.entity;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class EmployeeTest {
    private static Validator validator;
    private Employee employee;

    @BeforeEach
    void setUp() {
        if (validator == null) {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }
        employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("securePassword")
                .enabled(true)
                .emailCode("123456")
                .build();
    }

    @Test
    void shouldCreateEmployeeUsingBuilder() {
        // Then
        assertThat(employee).isNotNull();
        assertThat(employee.getFirstName()).isEqualTo("John");
        assertThat(employee.getLastName()).isEqualTo("Doe");
        assertThat(employee.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(employee.getPassword()).isEqualTo("securePassword");
        assertThat(employee.isEnabled()).isTrue();
        assertThat(employee.getEmailCode()).isEqualTo("123456");
    }


}