package pl.galushop.GaluShop.controller.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.galushop.GaluShop.dto.EmployeeRequest;
import pl.galushop.GaluShop.exception.ValidationException;
import pl.galushop.GaluShop.service.RegisterEmployeeService;

@Controller
@RequiredArgsConstructor
public class RegisterEmployeeController {
    private final RegisterEmployeeService registerEmployeeService;

    @PostMapping("/registerEmployee")
    public String saveNewEmployee(@RequestBody EmployeeRequest employeeRequest) {
        try {
            registerEmployeeService.saveEmployee(employeeRequest);
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }
}
