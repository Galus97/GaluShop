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

/**
 * Component responsible for validating user and employee registration.
 * Checks whether an email is already in use before allowing registration.
 */
@Component
@RequiredArgsConstructor
public class RegisterValidator {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final MessageService messageService;

    /**
     * Validates registration errors for a given object.
     * If the object is a User or Employee, it checks whether the email is already registered.
     *
     * @param object The object to validate (User or Employee).
     * @return A list of error messages if validation fails; otherwise, an empty list.
     */
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
