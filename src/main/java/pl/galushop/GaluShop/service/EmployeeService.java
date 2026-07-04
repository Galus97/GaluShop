package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.ServiceValidator;
import pl.galushop.GaluShop.component.ErrorMessages;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.request.EmployeeRequest;
import pl.galushop.GaluShop.dto.response.EmployeeResponse;
import pl.galushop.GaluShop.entity.Employee;
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

    /**
     * Retrieves an employee entity by their ID.
     *
     * @param employeeId The ID of the employee to retrieve.
     * @return The corresponding {@link Employee} entity.
     * @throws IllegalArgumentException  If the ID is {@code null} or invalid.
     * @throws EmployeeNotFoundException If no employee is found with the given ID.
     */
    public Employee getEmployeeEntity(Long employeeId) {
        serviceValidator.throwIfIdIsNotValid(employeeId, ErrorMessages.INVALID_EMPLOYEE_ID);
        return getEmployeeOrThrowIfNotFound(employeeId);
    }

    /**
     * Retrieves an employee as a response DTO by their ID.
     *
     * @param employeeId The ID of the employee.
     * @return A response DTO representing the employee.
     * @throws IllegalArgumentException  If the ID is {@code null} or invalid.
     * @throws EmployeeNotFoundException If no employee is found with the given ID.
     */
    public EmployeeResponse getEmployeeResponse(Long employeeId) {
        serviceValidator.throwIfIdIsNotValid(employeeId, ErrorMessages.INVALID_EMPLOYEE_ID);
        return EmployeeResponse.fromEntity(getEmployeeOrThrowIfNotFound(employeeId));
    }

    /**
     * Deletes an employee by their ID.
     *
     * @param employeeId The ID of the employee to delete.
     * @throws IllegalArgumentException  If the ID is {@code null} or invalid.
     * @throws EmployeeNotFoundException If no employee is found with the given ID.
     */
    public void deleteEmployee(Long employeeId) {
        serviceValidator.throwIfIdIsNotValid(employeeId, ErrorMessages.INVALID_EMPLOYEE_ID);
        employeeRepository.delete(getEmployeeOrThrowIfNotFound(employeeId));
    }

    /**
     * Updates an existing employee's information.
     * If a new password is provided, it is securely encoded.
     *
     * @param employeeRequest The request containing updated employee data.
     * @return A response DTO representing the updated employee.
     * @throws IllegalArgumentException  If the employee ID is {@code null} or invalid.
     * @throws EmployeeNotFoundException If no employee is found with the given ID.
     */
    @Transactional
    public EmployeeResponse updateEmployee(EmployeeRequest employeeRequest) {
        serviceValidator.throwIfIdIsNotValid(employeeRequest.getEmployeeId(), ErrorMessages.INVALID_EMPLOYEE_ID);
        serviceValidator.throwIfRequestIsNull(employeeRequest, ErrorMessages.EMPLOYEE_REQUEST_IS_NULL);

        Employee existingEmployee = getEmployeeOrThrowIfNotFound(employeeRequest.getEmployeeId());

        existingEmployee.setFirstName(employeeRequest.getFirstName());
        existingEmployee.setLastName(employeeRequest.getLastName());
        existingEmployee.setEmail(employeeRequest.getEmail());
        if (employeeRequest.getPassword() != null && !employeeRequest.getPassword().isBlank()) {
            existingEmployee.setPassword(passwordEncoder.encode(employeeRequest.getPassword()));
        }
        return EmployeeResponse.fromEntity(employeeRepository.save(existingEmployee));
    }

    /**
     * Retrieves an {@link Employee} entity by ID or throws an exception if not found.
     *
     * @param id The ID of the employee.
     * @return The corresponding employee entity.
     * @throws EmployeeNotFoundException If no employee is found with the given ID.
     */
    private Employee getEmployeeOrThrowIfNotFound(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(messageService.getMessage(ErrorMessages.EMPLOYEE_NOT_FOUND, id)));
    }
}
