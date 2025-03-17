package pl.galushop.GaluShop.dto.response;

public record UserDataResponse(Long userDataId, String city, String street, Integer streetNumber,
                            Integer apartmentNumber, String zipCode, Integer phoneNumber, Long userId) {

}
