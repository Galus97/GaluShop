package pl.galushop.GaluShop.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import pl.galushop.GaluShop.entity.Employee;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.cloud.config.enabled=false",
        "spring.config.import="
})
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    TestEntityManager entityManager;
    private Employee employee;

    @BeforeEach
    void setUp(){
        employee = Employee.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .password("secretPassword")
                .enabled(true)
                .emailCode("1111")
                .build();
    }

    @AfterEach
    void tearDown(){
        entityManager.clear();
    }

    @Test
    void givenExistedEmployee_whenFindByEmail_thenReturnSuccess(){
        //given
        entityManager.persistAndFlush(employee);
        //when
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail("jane.doe@example.com");
        //then
        assertTrue(optionalEmployee.isPresent());
        assertEquals(employee.getEmail(), optionalEmployee.get().getEmail());
    }
}