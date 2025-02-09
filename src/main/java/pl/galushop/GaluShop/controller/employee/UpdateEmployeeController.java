package pl.galushop.GaluShop.controller.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.EmployeeRequest;
import pl.galushop.GaluShop.entity.Employee;
import pl.galushop.GaluShop.service.EmployeeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class UpdateEmployeeController {
    private final EmployeeService employeeService;

    @PutMapping("/update")
    private ResponseEntity<Employee> updateEmployee(@RequestBody EmployeeRequest employeeRequest){
        employeeService.updateEmployee(employeeRequest);
        return ResponseEntity.ok(employeeService.getEmployee(employeeRequest.getEmployeeId()));
    }
}
