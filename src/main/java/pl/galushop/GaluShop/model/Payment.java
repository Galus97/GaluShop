package pl.galushop.GaluShop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import pl.galushop.GaluShop.component.PaymentStatus;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE payments SET is_deleted = true WHERE payment_id = ?")
@SQLRestriction("is_deleted = false")
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Min(1)
    private Double totalAmount;

    @NotNull
    private PaymentStatus paymentStatus;

    @OneToOne
    private Order order;

    @ManyToOne
    private User user;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
