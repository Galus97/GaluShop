package pl.galushop.GaluShop.dto;

import lombok.Data;

@Data
public class UserDataRequest {
    private String city;
    private String street;
    private Integer streetNumber;
    private Integer apartmentNumber;
    private String zipCode;
    private Integer phoneNumber;
    private Long userId;
}
