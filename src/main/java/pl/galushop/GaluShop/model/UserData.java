package pl.galushop.GaluShop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "userData")
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userDataId;

    @Size(min = 3)
    private String city;

    @Size(min = 3)
    private String street;

    @NotNull
    private Integer streetNumber;

    @NotNull
    private Integer apartmentNumber;

    @NotBlank
    private String zipCode;

    @NotNull
    private Integer phoneNumber;

    @OneToOne
    private User user;
}
