package pl.galushop.GaluShop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE user_data SET is_deleted = true WHERE user_data_id = ?")
@SQLRestriction("is_deleted = false")
@Table(name = "user_data")
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_data_id")
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

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
