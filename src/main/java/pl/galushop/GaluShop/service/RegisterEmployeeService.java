package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.component.ErrorMessages;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.component.RegisterValidator;
import pl.galushop.GaluShop.dto.request.EmployeeRequest;
import pl.galushop.GaluShop.dto.response.EmployeeResponse;
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
    private final MessageService messageService;

    /**
     * Registers a new employee by validating the provided data, encoding the password,
     * saving the employee entity to the database, and sending a verification email.
     *
     * @param employeeRequest The DTO containing registration details (name, email, password, etc.)
     * @return A response DTO with saved employee data.
     * @throws ValidationException If any validation error occurs during registration.
     */
    public EmployeeResponse saveNewEmployee(EmployeeRequest employeeRequest) throws ValidationException {
        throwIfUserRequestIsInvalid(employeeRequest);
        Employee employee = buildEmployeeFromRequest(employeeRequest);

        List<String> validationFailures = registerValidator.validateErrors(employee);
        if (validationFailures.isEmpty()) {
            emailService.sendEmail(employeeRequest.getEmail());
            return EmployeeResponse.fromEntity(employeeRepository.save(employee));
        } else {
            throw new ValidationException(validationFailures);
        }
    }

    /**
     * Validates if the employee request is not null.
     *
     * @param employeeRequest The Employee registration request.
     * @throws IllegalArgumentException If the request is null.
     */
    private void throwIfUserRequestIsInvalid(EmployeeRequest employeeRequest) {
        if (employeeRequest == null) {
            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.INVALID_EMPLOYEE_REQUEST));
        }
    }

    /**
     * Builds an Employee entity from the provided request data.
     * The password is encoded and a unique email verification code is generated.
     *
     * @param employeeRequest The request containing employee registration data.
     * @return A new Employee entity ready to be persisted.
     */
    private Employee buildEmployeeFromRequest(EmployeeRequest employeeRequest) {
        return Employee.builder()
                .firstName(employeeRequest.getFirstName())
                .lastName(employeeRequest.getLastName())
                .email(employeeRequest.getEmail())
                .emailCode(emailService.getVerificationCode(employeeRequest.getEmail()))
                .password(passwordEncoder.encode(employeeRequest.getPassword()))
                .build();
    }
}
