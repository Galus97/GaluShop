package pl.galushop.GaluShop.dto.request;

import lombok.Data;

@Data
public class UserRequest {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
