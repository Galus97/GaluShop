package pl.galushop.GaluShop.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.galushop.GaluShop.entity.Employee;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.repository.EmployeeRepository;
import pl.galushop.GaluShop.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RegisterValidator {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final MessageService messageService;

    public List<String> validateErrors(Object object) {
        List<String> errors = new ArrayList<>();

        if (object instanceof User user) {
            Optional<User> userExistByEmail = userRepository.findByEmail(user.getEmail());
            if (userExistByEmail.isPresent()) {
                errors.add(messageService.getMessage("error.emailAlreadyUsed"));
            }
        }

        if (object instanceof Employee employee) {
            Optional<Employee> employeeExistByEmail = employeeRepository.findByEmail(employee.getEmail());
            if (employeeExistByEmail.isPresent()) {
                errors.add(messageService.getMessage("error.emailAlreadyUsed"));
            }
        }
        return errors;
    }
}
