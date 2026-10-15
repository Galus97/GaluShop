package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.util.ServiceValidator;
import pl.galushop.GaluShop.component.ErrorMessages;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.request.EmployeeRequest;
import pl.galushop.GaluShop.dto.response.EmployeeResponse;
import pl.galushop.GaluShop.model.Employee;
import pl.galushop.GaluShop.exception.EmployeeNotFoundException;
import pl.galushop.GaluShop.repository.EmployeeRepository;

/**
 * Service class responsible for managing employee operations.
 * It provides functionality to retrieve, update, and delete employee records.
 */
@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageService messageService;
    private final ServiceValidator serviceValidator;


    public EmployeeResponse getEmployeeResponse(Employee employee) {
        return EmployeeResponse.fromEntity(employee);
    }

    @Transactional
    public EmployeeResponse updateEmployee(EmployeeRequest employeeRequest, Employee employee) {
        serviceValidator.throwIfRequestIsNull(employeeRequest, ErrorMessages.EMPLOYEE_REQUEST_IS_NULL);

        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setEmail(employeeRequest.getEmail());
        if (employeeRequest.getPassword() != null && !employeeRequest.getPassword().isBlank()) {
            employee.setPassword(passwordEncoder.encode(employeeRequest.getPassword()));
        }
        return EmployeeResponse.fromEntity(employeeRepository.save(employee));
    }

    @Transactional
    public void deleteEmployee(Employee employee) {
        employeeRepository.delete(employee);
    }
}
