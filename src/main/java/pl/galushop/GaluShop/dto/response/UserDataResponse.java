package pl.galushop.GaluShop.dto.response;

import pl.galushop.GaluShop.model.UserData;

public record UserDataResponse(Long userDataId, String city, String street, Integer streetNumber,
                               Integer apartmentNumber, String zipCode, Integer phoneNumber, Long userId) {

    public static UserDataResponse fromEntity(UserData userData) {
        return new UserDataResponse(
                userData.getUserDataId(),
                userData.getCity(),
                userData.getStreet(),
                userData.getStreetNumber(),
                userData.getApartmentNumber(),
                userData.getZipCode(),
                userData.getPhoneNumber(),
                userData.getUser().getUserId()
        );
    }
}
