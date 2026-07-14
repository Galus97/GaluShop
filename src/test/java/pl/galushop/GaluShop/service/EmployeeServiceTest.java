package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.request.EmployeeRequest;
import pl.galushop.GaluShop.dto.response.EmployeeResponse;
import pl.galushop.GaluShop.model.Employee;
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
    void givenExistingEmployeeId_whenGetEmployeeEntity_thenReturnsEmployee() {
        //given
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        //when
        Employee foundEmployee = employeeService.getEmployeeEntity(1L);
        //then
        assertNotNull(foundEmployee);
        assertEquals(1L, foundEmployee.getEmployeeId());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void givenNonExistingEmployeeId_whenGetEmployeeEntity_thenThrowsException() {
        //given
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageService.getMessage(any(), any())).thenReturn("Employee not found");
        //then
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeEntity(1L));
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void givenExistingEmployeeId_whenGetEmployeeResponse_thenReturnsResponse() {
        //given
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        //when
        EmployeeResponse response = employeeService.getEmployeeResponse(1L);
        //then
        assertNotNull(response);
        assertEquals("John", response.firstName());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void givenExistingEmployeeId_whenDeleteEmployee_thenDeletesEmployee() {
        //given
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        //when
        employeeService.deleteEmployee(1L);
        //then
        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    void givenNonExistingEmployeeId_whenDeleteEmployee_thenThrowsException() {
        //given
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageService.getMessage(any(), any())).thenReturn("Employee not found");
        //then
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployee(1L));
        verify(employeeRepository, never()).delete(any());
    }

    @Test
    void givenValidEmployeeRequest_whenUpdateEmployee_thenUpdatesEmployeeDetails() {
        //given
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
        //when
        EmployeeResponse response = employeeService.updateEmployee(request);
        //then
        assertNotNull(response);
        assertEquals("Jane", response.firstName());
        assertEquals("Smith", response.lastName());
        assertEquals("jane.smith@example.com", response.email());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void givenNonExistingEmployeeId_whenUpdateEmployee_thenThrowsException() {
        //given
        EmployeeRequest request = EmployeeRequest.builder()
                .employeeId(1L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .password("newPassword")
                .build();
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageService.getMessage(any(), any())).thenReturn("Employee not found");
        //then
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.updateEmployee(request));
    }
}
