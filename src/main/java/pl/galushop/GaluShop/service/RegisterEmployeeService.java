package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.component.RegisterValidator;
import pl.galushop.GaluShop.entity.Employee;
import pl.galushop.GaluShop.exception.ValidationException;
import pl.galushop.GaluShop.repository.EmployeeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterEmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegisterValidator registerValidator;

    public void saveNewEmployee(Employee employee) throws ValidationException {
        List<String> validationFailures = registerValidator.validateUserErrors(employee);
        if(validationFailures.isEmpty()){
            employee.setEmployeeId(null);
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
            employeeRepository.save(employee);
        } else {
            throw new ValidationException(validationFailures);
        }
    }
}
