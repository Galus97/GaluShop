package pl.galushop.GaluShop.dto.response;

import pl.galushop.GaluShop.model.Employee;

public record EmployeeResponse(Long employeeId, String firstName, String lastName, String email,
                               boolean enabled) {
    public static EmployeeResponse fromEntity(Employee employee) {
        return new EmployeeResponse(
                employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.isEnabled()
        );
    }
}
