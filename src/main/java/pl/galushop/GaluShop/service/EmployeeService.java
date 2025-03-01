package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.dto.EmployeeRequest;
import pl.galushop.GaluShop.entity.Employee;
import pl.galushop.GaluShop.exception.EmployeeNotFoundException;
import pl.galushop.GaluShop.repository.EmployeeRepository;

import java.util.NoSuchElementException;

/**
 * Service class responsible for managing employee operations.
 */
@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Retrieves an employee by their ID.
     *
     * @param employeeId The ID of the employee to retrieve.
     * @return The retrieved employee entity.
     * @throws EmployeeNotFoundException if no employee is found with the given ID.
     */
    public Employee getEmployee(Long employeeId){
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + employeeId + " not found"));
    }

    /**
     * Deletes an employee by their ID.
     *
     * @param employeeId The ID of the employee to delete.
     * @throws EmployeeNotFoundException if no employee is found with the given ID.
     */
    public void deleteEmployee(Long employeeId){
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + employeeId + " not found"));
        employeeRepository.delete(employee);
    }

    /**
     * Updates an existing employee's details.
     * If a new password is provided, it will be encoded before saving.
     *
     * @param employeeRequest The request object containing updated employee details.
     * @throws EmployeeNotFoundException if no employee is found with the given ID.
     */
    @Transactional
    public void updateEmployee(EmployeeRequest employeeRequest){
        Employee existingEmployee = employeeRepository.findById(employeeRequest.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + employeeRequest.getEmployeeId() + " not found"));
        existingEmployee.setFirstName(existingEmployee.getFirstName());
        existingEmployee.setLastName(existingEmployee.getLastName());
        existingEmployee.setEmail(existingEmployee.getEmail());
        if(employeeRequest.getPassword() != null && !employeeRequest.getPassword().isBlank()){
            existingEmployee.setPassword(passwordEncoder.encode(employeeRequest.getPassword()));
        }

        employeeRepository.save(existingEmployee);
    }

}
