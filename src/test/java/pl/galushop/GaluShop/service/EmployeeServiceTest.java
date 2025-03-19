package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.request.EmployeeRequest;
import pl.galushop.GaluShop.dto.response.EmployeeResponse;
import pl.galushop.GaluShop.entity.Employee;
import pl.galushop.GaluShop.exception.EmployeeNotFoundException;
import pl.galushop.GaluShop.repository.EmployeeRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setEmployeeId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setPassword("encodedPassword");
    }

    @Test
    void shouldReturnEmployeeEntityWhenExists() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee foundEmployee = employeeService.getEmployeeEntity(1L);

        assertNotNull(foundEmployee);
        assertEquals(1L, foundEmployee.getEmployeeId());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenEmployeeNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageService.getMessage(any(), any())).thenReturn("Employee not found");

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeEntity(1L));
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void shouldDeleteEmployeeWhenExists() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageService.getMessage(any(), any())).thenReturn("Employee not found");

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployee(1L));
        verify(employeeRepository, never()).delete(any());
    }

    @Test
    void shouldUpdateEmployeeDetails() {
        EmployeeRequest request = EmployeeRequest.builder()
                .employeeId(1L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .password("newPassword")
                .build();

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        EmployeeResponse response = employeeService.updateEmployee(request);

        assertNotNull(response);
        assertEquals("Jane", response.firstName());
        assertEquals("Smith", response.lastName());
        assertEquals("jane.smith@example.com", response.email());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentEmployee() {
        EmployeeRequest request = EmployeeRequest.builder()
                .employeeId(1L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .password("newPassword")
                .build();
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageService.getMessage(any(), any())).thenReturn("Employee not found");

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.updateEmployee(request));
    }
}
