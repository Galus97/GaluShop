package pl.galushop.GaluShop.dto;

import lombok.Data;

@Data
public class EmployeeRequest {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
