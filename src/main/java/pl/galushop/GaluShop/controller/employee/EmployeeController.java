package pl.galushop.GaluShop.controller.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
public class EmployeeController {
    private final EmployeeService employeeService;
    private final RegisterEmployeeService registerEmployeeService;

    @GetMapping("/{id}")
    public ResponseEntity<Employee> showEmployee(@PathVariable Long id){
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }

    @PostMapping
    public ResponseEntity<Employee> saveEmployee(@RequestBody EmployeeRequest employeeRequest) {
        try {
            registerEmployeeService.saveEmployee(employeeRequest);
            return ResponseEntity.ok(employeeService.getEmployee(employeeRequest.getEmployeeId()));
        } catch (ValidationException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping
    private ResponseEntity<Employee> updateEmployee(@RequestBody EmployeeRequest employeeRequest){
        employeeService.updateEmployee(employeeRequest);
        return ResponseEntity.ok(employeeService.getEmployee(employeeRequest.getEmployeeId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
