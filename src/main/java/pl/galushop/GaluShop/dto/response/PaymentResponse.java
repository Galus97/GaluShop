package pl.galushop.GaluShop.dto.response;

import pl.galushop.GaluShop.component.PaymentStatus;
import pl.galushop.GaluShop.entity.Payment;

public record PaymentResponse(Long paymentId, Double totalAmount, PaymentStatus paymentStatus, Long orderId,
                              Long userId) {

    public static PaymentResponse fromEntity(Payment payment) {
        return new PaymentResponse(
                payment.getPaymentId(),
                payment.getTotalAmount(),
                payment.getPaymentStatus(),
                payment.getOrder().getOrderId(),
                payment.getUser().getUserId()
        );
    }
}
