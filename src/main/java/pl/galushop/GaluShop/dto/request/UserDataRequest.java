package pl.galushop.GaluShop.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDataRequest {
    private Long userDataId;
    private String city;
    private String street;
    private Integer streetNumber;
    private Integer apartmentNumber;
    private String zipCode;
    private Integer phoneNumber;
    private Long userId;
}
