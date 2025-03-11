package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.component.CurrentEmployee;
import pl.galushop.GaluShop.component.CurrentUser;
import pl.galushop.GaluShop.component.ErrorMessages;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.entity.Employee;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.repository.EmployeeRepository;
import pl.galushop.GaluShop.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final MessageService messageService;

    /**
     * Loads a user or an employee by their email address.
     *
     * @param email the email of the user or employee
     * @return {@link UserDetails} representing the authenticated user or employee
     * @throws UsernameNotFoundException if no user or employee is found with the given email
     * @throws IllegalArgumentException if email is null or blank
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        throwIfEmailIsInvalid(email);
        // Try to find the user in the database
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new CurrentUser(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")), user);
        }

        // Try to find the employee in the database
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            return new CurrentEmployee(
                    employee.getEmail(),
                    employee.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_EMPLOYEE")), employee);
        }
        throw new UsernameNotFoundException(messageService.getMessage(ErrorMessages.USER_OR_EMPLOYEE_NOT_FOUND, email));
    }

    private void throwIfEmailIsInvalid(String email){
        if(email == null || email.isBlank()){
            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.EMAIL_IS_INVALID, email));
        }
    }
}
