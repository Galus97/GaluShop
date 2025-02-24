package pl.galushop.GaluShop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;
import pl.galushop.GaluShop.component.PaymentStatus;

@Entity
@Getter
@Setter
@ToString
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long paymentsId;

    private Double price;

    private PaymentStatus paymentStatus;

    @OneToOne
    private Order order;
}
