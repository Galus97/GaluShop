package pl.galushop.GaluShop.controller.all;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.response.PaymentResponse;
import pl.galushop.GaluShop.service.PaymentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class UserAllPaymentsController {
    private final PaymentService paymentService;

    @GetMapping("/user/{id}")
    public ResponseEntity<List<PaymentResponse>> showAllUserPayments(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getAllPaymentResponseByUserId(id));
    }
}
