package pl.galushop.GaluShop.dto;

import lombok.Data;
import pl.galushop.GaluShop.component.PaymentStatus;

@Data
public class PaymentRequest {
    private Long paymentId;
    private Double totalAmount;
    private PaymentStatus paymentStatus;
    private Long orderId;
}
