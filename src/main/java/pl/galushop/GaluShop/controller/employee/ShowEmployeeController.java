package pl.galushop.GaluShop.controller.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.EmployeeRequest;
import pl.galushop.GaluShop.entity.Employee;
import pl.galushop.GaluShop.service.EmployeeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class ShowEmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/show/{id}")
    public ResponseEntity<Employee> showEmployeeInfo(@PathVariable Long id){
        return ResponseEntity.ok(employeeService.showEmployeeInf(id));
    }
}
