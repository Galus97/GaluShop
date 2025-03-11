package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.entity.Employee;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.repository.EmployeeRepository;
import pl.galushop.GaluShop.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

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

    @Test
    void givenExistingUser_whenLoadUserByUsername_thenReturnUserDetails(){
        // Arrange
        when(userRepository.findByEmail("john.doe@gmail.com")).thenReturn(Optional.of(user));
        //Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("john.doe@gmail.com");
        //Assert
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo("john.doe@gmail.com");
        verify(userRepository, times(1)).findByEmail("john.doe@gmail.com");
        verifyNoInteractions(employeeRepository);
    }
}