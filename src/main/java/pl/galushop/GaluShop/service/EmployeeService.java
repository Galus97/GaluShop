package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.entity.Employee;
import pl.galushop.GaluShop.exception.EmployeeNotFoundException;
import pl.galushop.GaluShop.repository.EmployeeRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Employee getEmployee(Long employeeId){
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + employeeId + " not found"))
    }

    public void deleteEmployee(Long employeeId){
        if(employeeId != null && employeeId > 0){
            if(employeeRepository.existsById(employeeId)){
                employeeRepository.deleteById(employeeId);
            } else {
                throw new NoSuchElementException("This employee doesn't exist in database");
            }
        } else {
            throw new IllegalArgumentException("Employee Id is invalid");
        }
    }

    public void updateEmployee(Long employeeId, String firstName, String lastName, String email, String password){
        if(employeeId != null && employeeId > 0) {
            if (employeeRepository.existsById(employeeId)) {
                employeeRepository.updateEmployeeByEmployeeId(employeeId, firstName, lastName, email, password);
            } else {
                throw new NoSuchElementException("This employee doesn't exist in database");
            }
        } else {
            throw new IllegalArgumentException("Employee Id is invalid");
        }
    }

}
