package pl.galushop.GaluShop.dto.response;

public record EmployeeResponse(Long employeeId, String firstName, String lastName, String email,
                               boolean enabled, String emailCode) {

}
