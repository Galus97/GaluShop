package pl.galushop.GaluShop.controller.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.EmployeeRequest;
import pl.galushop.GaluShop.entity.Employee;
import pl.galushop.GaluShop.exception.ValidationException;
import pl.galushop.GaluShop.service.EmployeeService;
import pl.galushop.GaluShop.service.RegisterEmployeeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class RegisterEmployeeController {
    private final RegisterEmployeeService registerEmployeeService;
    private final EmployeeService employeeService;

    @PostMapping("/register")
    public ResponseEntity<Employee> saveEmployee(@RequestBody EmployeeRequest employeeRequest) {
        try {
            registerEmployeeService.saveEmployee(employeeRequest);
            return ResponseEntity.ok(employeeService.getEmployee(employeeRequest.getEmployeeId()));
        } catch (ValidationException e) {
            return ResponseEntity.noContent().build();
        }
    }
}
