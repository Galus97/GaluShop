package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.galushop.GaluShop.util.RegisterValidator;
import pl.galushop.GaluShop.dto.request.EmployeeRequest;
import pl.galushop.GaluShop.dto.response.EmployeeResponse;
import pl.galushop.GaluShop.model.Employee;
import pl.galushop.GaluShop.repository.EmployeeRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterEmployeeServiceTest {
    @Mock
    EmployeeRepository repository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    RegisterValidator registerValidator;
    @Mock
    EmailService emailService;
    @InjectMocks
    RegisterEmployeeService service;

    @Test
    void givenCorrectRequest_whenSaveNewEmployee_thenReturnsEmployeeResponse() throws Exception{
        //given
        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .employeeId(null)
                .firstName("John")
                .lastName("Smith")
                .email("john.smith@mail.com")
                .password("password")
                .build();

        Employee employee = Employee.builder()
                .employeeId(1L)
                .firstName("John")
                .lastName("Smith")
                .email("john.smith@mail.com")
                .password("password")
                .enabled(true)
                .emailCode("1111")
                .build();
        when(repository.save(any(Employee.class))).thenReturn(employee);
        //when
        EmployeeResponse response = service.saveNewEmployee(employeeRequest);
        //then
        assertNotNull(response);
        assertEquals("John", response.firstName());
        assertEquals("Smith", response.lastName());
        assertEquals("john.smith@mail.com", response.email());
        assertEquals("1111", response.emailCode());
        assertTrue(response.enabled());
        verify(repository, times(1)).save(any(Employee.class));
    }

}