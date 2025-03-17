package pl.galushop.GaluShop.controller;

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
import pl.galushop.GaluShop.dto.request.EmployeeRequest;
import pl.galushop.GaluShop.entity.Employee;
import pl.galushop.GaluShop.exception.ValidationException;
import pl.galushop.GaluShop.service.EmployeeService;
import pl.galushop.GaluShop.service.RegisterEmployeeService;

import java.net.URI;

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
    public ResponseEntity<?> saveEmployee(@RequestBody EmployeeRequest employeeRequest) {
        try {
            Employee savedEmployee = registerEmployeeService.saveNewEmployee(employeeRequest);
            return ResponseEntity.created(URI.create("/employee/" + savedEmployee.getEmployeeId()))
                    .body(savedEmployee);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getValidationErrors());
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
