package pl.galushop.GaluShop.controller.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/employee/show")
    public Employee showEmployeeInfo(@RequestBody EmployeeRequest employeeRequest){
        return employeeService.showEmployeeInf(employeeRequest.getEmployeeId());
    }
}
