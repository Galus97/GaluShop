package pl.galushop.GaluShop.controller.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.EmployeeRequest;
import pl.galushop.GaluShop.service.EmployeeService;

@RestController
@RequiredArgsConstructor
public class UpdateEmployeeController {
    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/updateEmployee")
    private String updateEmployee(@RequestBody EmployeeRequest employeeRequest){
        employeeService.updateEmployee(
                employeeRequest.getEmployeeId(),
                employeeRequest.getFirstName(),
                employeeRequest.getLastName(),
                employeeRequest.getEmail(),
                passwordEncoder.encode(employeeRequest.getPassword())
        );

        return "Success";
    }
}
