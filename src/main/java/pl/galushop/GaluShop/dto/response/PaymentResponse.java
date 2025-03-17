package pl.galushop.GaluShop.dto.response;

import pl.galushop.GaluShop.component.PaymentStatus;

public record PaymentResponse(Long paymentId, Double totalAmount, PaymentStatus paymentStatus, Long orderId, Long userId) {

}
