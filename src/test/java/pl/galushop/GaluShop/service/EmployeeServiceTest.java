package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.entity.Employee;
import pl.galushop.GaluShop.repository.EmployeeRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void givenExistingId_whenGetEmployee_thenReturnEmployee(){
        //Arrange
        Employee employee = new Employee();
        employee.setEmployeeId(1L);
        when(employeeRepository.findById(employee.getEmployeeId())).thenReturn(Optional.of(employee));
        //Act
        Employee resultEmployee = employeeService.getEmployee(employee.getEmployeeId());
        //Assert
        assertThat(resultEmployee).isEqualTo(employee);
    }
}