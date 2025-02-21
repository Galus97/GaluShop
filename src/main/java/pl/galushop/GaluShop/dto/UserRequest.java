package pl.galushop.GaluShop.dto;

import lombok.Data;

@Data
public class UserRequest {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
