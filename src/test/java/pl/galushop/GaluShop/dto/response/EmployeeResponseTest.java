package pl.galushop.GaluShop.dto.response;

import org.junit.jupiter.api.Test;
import pl.galushop.GaluShop.entity.Employee;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeResponseTest {

    @Test
    void givenEmployeeEntity_whenFromEntity_thenReturnsCorrectResponse() {
        // given
        Employee employee = new Employee();
        employee.setEmployeeId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setEnabled(true);
        employee.setEmailCode("abc123");

        // when
        EmployeeResponse response = EmployeeResponse.fromEntity(employee);

        // then
        assertEquals(1L, response.employeeId());
        assertEquals("John", response.firstName());
        assertEquals("Doe", response.lastName());
        assertEquals("john.doe@example.com", response.email());
        assertTrue(response.enabled());
        assertEquals("abc123", response.emailCode());
    }

}