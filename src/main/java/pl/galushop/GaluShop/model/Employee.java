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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE employee SET is_deleted = true WHERE employee_id = ?")
@SQLRestriction("is_deleted = false")
@Builder
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    @Size(min = 3)
    private String firstName;

    @Size(min = 3)
    private String lastName;

    @Length(min = 5)
    @Column(unique = true)
    @Email
    private String email;

    @Length(min = 5)
    private String password;

    private boolean enabled;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public Employee(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
