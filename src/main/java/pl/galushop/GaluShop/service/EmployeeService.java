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

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public Employee getEmployee(Long employeeId){
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + employeeId + " not found"));
    }

    public void deleteEmployee(Long employeeId){
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + employeeId + " not found"));
        employeeRepository.delete(employee);
    }

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
