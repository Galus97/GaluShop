package pl.galushop.GaluShop.controller.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.entity.Employee;
import pl.galushop.GaluShop.service.EmployeeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity<Employee> showEmployee(@PathVariable Long id){
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
