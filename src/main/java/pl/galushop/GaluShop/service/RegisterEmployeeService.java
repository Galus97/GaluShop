package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.component.RegisterValidator;
import pl.galushop.GaluShop.dto.EmployeeRequest;
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
    private final EmailService emailService;

    //Dokończyć
    public void saveEmployee(EmployeeRequest employeeRequest) throws ValidationException{
        Employee employee = new Employee();
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setEmail(employeeRequest.getEmail());

        List<String> validationFailures = registerValidator.validateErrors(employee);
        if(validationFailures.isEmpty()){
            employee.setEmailCode(emailService.emailCodeValue());
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
            employeeRepository.save(employee);
            emailService.sendEmail(employeeRequest.getEmail());
        } else {
            throw new ValidationException(validationFailures);
        }
    }
}
