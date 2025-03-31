package pl.galushop.GaluShop.dto.request;

import lombok.Builder;
import lombok.Data;
import pl.galushop.GaluShop.component.PaymentStatus;

@Data
@Builder
public class PaymentRequest {
    private Long paymentId;
    private Double totalAmount;
    private PaymentStatus paymentStatus;
    private Long orderId;
    private Long userId;
}
