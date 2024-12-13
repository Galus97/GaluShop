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

    private static final String ERROR_MESSAGE = "Ten adres email jest już używany. Wpisz inny adres email";
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    public List<String> validateUserErrors(Object object) {
        List<String> errors = new ArrayList<>();

        if(object instanceof User user) {
            Optional<User> userExistByEmail = userRepository.findByEmail(user.getEmail());
            if (userExistByEmail.isPresent()) {
                errors.add(ERROR_MESSAGE);
            }
        }

        if(object instanceof Employee employee){
            Optional<Employee> employeeExistByEmail = employeeRepository.findByEmail(employee.getEmail());
            if(employeeExistByEmail.isPresent()){
                errors.add(ERROR_MESSAGE);
            }
        }

        return errors;
    }
}
