package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.component.ErrorMessages;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.request.EmployeeRequest;
import pl.galushop.GaluShop.dto.response.EmployeeResponse;
import pl.galushop.GaluShop.entity.Employee;
import pl.galushop.GaluShop.exception.EmployeeNotFoundException;
import pl.galushop.GaluShop.repository.EmployeeRepository;

/**
 * Service class responsible for managing employee operations.
 */
@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageService messageService;

    /**
     * Retrieves an employee by their ID.
     *
     * @param employeeId The ID of the employee to retrieve.
     * @return The retrieved employee entity.
     * @throws EmployeeNotFoundException if no employee is found with the given ID.
     */
    public Employee getEmployeeEntity(Long employeeId){
        throwIfIdIsInvalid(employeeId);
        return getEmployeeOrThrowIfNotFound(employeeId);
    }

    public EmployeeResponse getEmployeeResponse(Long employeeId){
        throwIfIdIsInvalid(employeeId);
        return EmployeeResponse.fromEntity(getEmployeeOrThrowIfNotFound(employeeId));
    }

    /**
     * Deletes an employee by their ID.
     *
     * @param employeeId The ID of the employee to delete.
     * @throws IllegalArgumentException if id is null or less then 1
     * @throws EmployeeNotFoundException if no employee is found with the given ID.
     */
    public void deleteEmployee(Long employeeId){
        throwIfIdIsInvalid(employeeId);

        employeeRepository.delete(getEmployeeOrThrowIfNotFound(employeeId));
    }

    /**
     * Updates an existing employee's details.
     * If a new password is provided, it will be encoded before saving.
     *
     * @param employeeRequest The request object containing updated employee details.
     * @throws EmployeeNotFoundException if no employee is found with the given ID.
     */
    @Transactional
    public EmployeeResponse updateEmployee(EmployeeRequest employeeRequest){
        Employee existingEmployee = getEmployeeOrThrowIfNotFound(employeeRequest.getEmployeeId());

        existingEmployee.setFirstName(employeeRequest.getFirstName());
        existingEmployee.setLastName(employeeRequest.getLastName());
        existingEmployee.setEmail(employeeRequest.getEmail());
        if(employeeRequest.getPassword() != null && !employeeRequest.getPassword().isBlank()){
            existingEmployee.setPassword(passwordEncoder.encode(employeeRequest.getPassword()));
        }

        return EmployeeResponse.fromEntity(employeeRepository.save(existingEmployee));
    }

    private Employee getEmployeeOrThrowIfNotFound(Long id){
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(messageService.getMessage(ErrorMessages.EMPLOYEE_NOT_FOUND, id)));
    }

    private void throwIfIdIsInvalid(Long id){
        if(id == null || id <= 0){
            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.INVALID_EMPLOYEE_ID, id));
        }
    }
}
