package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.response.PaymentResponse;
import pl.galushop.GaluShop.service.PaymentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment/order")
public class PaymentOrderController {
    private final PaymentService paymentService;

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> showOrderPayment(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentResponseByOrderId(id));
    }
}
