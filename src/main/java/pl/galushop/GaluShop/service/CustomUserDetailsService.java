package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.component.CurrentEmployee;
import pl.galushop.GaluShop.component.CurrentUser;
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new CurrentUser(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")), user);
        }
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
        if(optionalEmployee.isPresent()){
            Employee employee = optionalEmployee.get();
            return new CurrentEmployee(
                    employee.getEmail(),
                    employee.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_EMPLOYEE")), employee);
        }
        throw new UsernameNotFoundException("No user or employee found with email: " + email);
    }
}
