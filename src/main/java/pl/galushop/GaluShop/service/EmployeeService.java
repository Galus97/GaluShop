package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.repository.EmployeeRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public void deleteEmployee(Long employeeId){
        if(employeeId != null && employeeId > 0){
            if(employeeRepository.findById(employeeId).isPresent()){
                employeeRepository.deleteById(employeeId);
            } else {
                throw new NoSuchElementException("This employee doesn't exist in database");
            }
        } else {
            throw new IllegalArgumentException("Employee Id is invalid");
        }
    }
}
