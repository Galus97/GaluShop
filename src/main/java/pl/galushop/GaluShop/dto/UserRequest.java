package pl.galushop.GaluShop.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
