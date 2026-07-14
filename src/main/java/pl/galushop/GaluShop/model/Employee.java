package pl.galushop.GaluShop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @Size(min = 3)
    private String firstName;

    @Size(min = 3)
    private String lastName;

    @Size(min = 5)
    @Column(unique = true)
    @Email
    private String email;

    @Size(min = 5)
    private String password;

    private boolean enabled;

    private String emailCode;

    public Employee(String firstName, String lastName, String email, String password, String emailCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.emailCode = emailCode;
    }
}
