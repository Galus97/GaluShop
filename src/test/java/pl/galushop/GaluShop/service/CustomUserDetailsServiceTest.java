package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.entity.Employee;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.repository.EmployeeRepository;
import pl.galushop.GaluShop.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    MessageService messageService;
    @InjectMocks
    CustomUserDetailsService customUserDetailsService;

    private User user;
    private Employee employee;

    @BeforeEach
    void setUp(){
        user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .password("{noop}password")
                .enabled(true)
                .emailCode("1111")
                .build();

        employee = Employee.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .password("{noop}password")
                .enabled(true)
                .emailCode("2222")
                .build();
    }
}