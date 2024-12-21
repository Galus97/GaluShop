package pl.galushop.GaluShop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userDataId;

    @Size(min = 3)
    private String city;

    @Size(min = 3)
    private String street;

    @NotBlank
    private Integer streetNumber;

    @NotBlank
    private Integer apartmentNumber;

    @NotBlank
    private String zipCode;

    @NotBlank
    private Integer phoneNumber;

    @OneToOne
    private User user;
}
