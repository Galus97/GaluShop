package pl.galushop.GaluShop.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
