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
import pl.galushop.GaluShop.entity.Employee;
import pl.galushop.GaluShop.exception.EmployeeNotFoundException;
import pl.galushop.GaluShop.repository.EmployeeRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    MessageService messageService;
    @InjectMocks
    EmployeeService employeeService;

    private Employee existingEmployee;

    @BeforeEach
    void setUp() {
        existingEmployee = Employee.builder()
                .employeeId(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("hashedPassword")
                .enabled(true)
                .build();
    }

    @Test
    void givenExistingId_whenGetEmployee_thenReturnEmployee(){
        //Arrange
        Employee employee = new Employee();
        employee.setEmployeeId(1L);
        when(employeeRepository.findById(employee.getEmployeeId())).thenReturn(Optional.of(employee));
        //Act
        Employee resultEmployee = employeeService.getEmployeeEntity(employee.getEmployeeId());
        //Assert
        assertThat(resultEmployee).isEqualTo(employee);
    }

    @Test
    void givenNonExistentId_whenGetEmployee_thenThrowEmployeeNotFoundException(){
        //Arrange
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        //Act & Assert
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.getEmployeeEntity(9999L);
        });
    }

    @Test
    void givenInvalidId_whenThrowIfIdIsInvalid_thenReturnIllegalArgumentException(){
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            employeeService.getEmployeeEntity(-1L);
        });
    }

    @Test
    void givenNullId_whenThrowIfIdIsInvalid_thenReturnIllegalArgumentException(){
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            employeeService.getEmployeeEntity(null);
        });
    }

    @Test
    void givenExistingId_whenDeleteEmployee_thenEmployeeIsDeleted(){
        //Arrange
        Employee employee = new Employee();
        employee.setEmployeeId(1L);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(employee);
        //Act & Assert
        assertDoesNotThrow(() -> employeeService.deleteEmployee(employee.getEmployeeId()));
        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    void givenNonExistentId_whenDeleteEmployee_thenThrowEmployeeNotFoundException(){
        //Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
        //Act & Assert
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.deleteEmployee(1L);
        });
    }

    @Test
    void givenInvalidId_whenDeleteEmployee_thenThrowIllegalArgumentException(){
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            employeeService.deleteEmployee(-1L);
        });
    }

    @Test
    void givenNullId_whenDeleteEmployee_thenThrowIllegalArgumentException(){
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            employeeService.deleteEmployee(null);
        });
    }

    @Test
    void givenExistingEmployee_whenUpdateEmployee_thenUpdatedValuesCorrectly(){
        //Arrange
        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .employeeId(1L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .password("newPassword")
                .build();

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existingEmployee));
        when(passwordEncoder.encode("newPassword")).thenReturn("hashedNewPassword");
        //Act
        employeeService.updateEmployee(employeeRequest);
        //Assert
        assertEquals("Jane", existingEmployee.getFirstName());
        assertEquals("Smith", existingEmployee.getLastName());
        assertEquals("jane.smith@example.com", existingEmployee.getEmail());
        assertEquals("hashedNewPassword", existingEmployee.getPassword());
        verify(employeeRepository).save(existingEmployee);
    }

    @Test
    void givenRequestWithoutPassword_whenUpdateEmployee_thenUpdatedValuesCorrectly(){
        //Arrange
        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .employeeId(1L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .password(null)
                .build();

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existingEmployee));
        //Act
        employeeService.updateEmployee(employeeRequest);
        //Assert
        assertEquals("Jane", existingEmployee.getFirstName());
        assertEquals("Smith", existingEmployee.getLastName());
        assertEquals("jane.smith@example.com", existingEmployee.getEmail());
        assertEquals("hashedPassword", existingEmployee.getPassword());
        verify(employeeRepository).save(existingEmployee);
    }

    @Test
    void givenNonExistentEmployee_whenUpdateEmployee_thenThrowEmployeeNotFoundException(){
        //Arrange
        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .employeeId(9999L)
                .firstName("Mark")
                .lastName("ZuckerBerg")
                .email("mark@example.com")
                .password("oldPassword")
                .build();
        when(employeeRepository.findById(9999L)).thenReturn(Optional.empty());
        //Act & Assert
        assertThrows(EmployeeNotFoundException.class, () -> {
           employeeService.updateEmployee(employeeRequest);
        });
        verify(employeeRepository, never()).save(any());
    }
}