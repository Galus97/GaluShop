package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.component.RegisterValidator;
import pl.galushop.GaluShop.dto.EmployeeRequest;
import pl.galushop.GaluShop.entity.Employee;
import pl.galushop.GaluShop.exception.ValidationException;
import pl.galushop.GaluShop.repository.EmployeeRepository;

import java.util.List;

/**
 * Service class handling employee registration process.
 * This includes validation, password encoding, saving the employee to the database, and sending a verification email.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class RegisterEmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegisterValidator registerValidator;
    private final EmailService emailService;

    /**
     * Registers a new employee by validating input data, encoding the password,
     * saving the employee to the database, and sending a verification email.
     *
     * @param employeeRequest The request object containing user registration details.
     * @throws ValidationException if the validation fails.
     */
    public void saveEmployee(EmployeeRequest employeeRequest) throws ValidationException{
        Employee employee = Employee.builder()
                .firstName(employeeRequest.getFirstName())
                .lastName(employeeRequest.getLastName())
                .email(employeeRequest.getEmail())
                .emailCode(emailService.getVerificationCode(employeeRequest.getEmail()))
                .password(passwordEncoder.encode(employeeRequest.getPassword()))
                .build();

        List<String> validationFailures = registerValidator.validateErrors(employee);
        if(validationFailures.isEmpty()){
            employeeRepository.save(employee);
            emailService.sendEmail(employeeRequest.getEmail());
        } else {
            throw new ValidationException(validationFailures);
        }
    }
}
