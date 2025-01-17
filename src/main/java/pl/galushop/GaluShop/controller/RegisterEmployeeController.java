package pl.galushop.GaluShop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.galushop.GaluShop.dto.EmployeeRequest;
import pl.galushop.GaluShop.entity.Employee;
import pl.galushop.GaluShop.exception.ValidationException;
import pl.galushop.GaluShop.service.RegisterEmployeeService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RegisterEmployeeController {
    private final RegisterEmployeeService registerEmployeeService;

    @GetMapping("/registerEmployee")
    public String showRegisterEmployeePage(Model model) {
        model.addAttribute("employee", new Employee());
        return "registerEmployee";
    }

    @PostMapping("/registerEmployee")
    public String saveNewEmployee(@Valid Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registerEmployee";
        }
        try {
            registerEmployeeService.saveNewEmployee(employee);
        } catch (ValidationException exception) {
            List<String> errors = exception.getValidationErrors();
            bindingResult.rejectValue("email", "", errors.get(0));
        }

        return "redirect:login";
    }

    @PostMapping("/registerEmployee")
    public String saveNewEmployee(@RequestBody EmployeeRequest employeeRequest){

        Employee employee = new Employee();
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setEmail(employeeRequest.getEmail());
        employee.setPassword(employeeRequest.getPassword());

        try {
            registerEmployeeService.saveNewEmployee(employee);
        } catch (ValidationException exception) {
            List<String> validationErrors = exception.getValidationErrors();
            for (String error : validationErrors) {
                System.out.println(error);
            }
            return "faild";
        }

        return "succes";
    }
}
