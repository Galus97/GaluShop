package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.request.EmployeeRequest;
import pl.galushop.GaluShop.dto.response.EmployeeResponse;
import pl.galushop.GaluShop.exception.ValidationException;
import pl.galushop.GaluShop.model.Employee;
import pl.galushop.GaluShop.service.EmployeeService;
import pl.galushop.GaluShop.service.RegisterEmployeeService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final RegisterEmployeeService registerEmployeeService;

    @GetMapping
    public ResponseEntity<EmployeeResponse> showEmployee(@AuthenticationPrincipal Employee employee) {
        return ResponseEntity.ok(employeeService.getEmployeeResponse(employee));
    }

    @PostMapping
    public ResponseEntity<?> saveEmployee(@RequestBody EmployeeRequest employeeRequest) {
        try {
            EmployeeResponse savedEmployee = registerEmployeeService.saveNewEmployee(employeeRequest);
            return ResponseEntity.created(URI.create("/employee/" + savedEmployee.employeeId()))
                    .body(savedEmployee);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getValidationErrors());
        }
    }

    @PutMapping
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @RequestBody EmployeeRequest employeeRequest,
            @AuthenticationPrincipal Employee employee) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeRequest, employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@AuthenticationPrincipal Employee employee) {
        employeeService.deleteEmployee(employee);
        return ResponseEntity.noContent().build();
    }
}
