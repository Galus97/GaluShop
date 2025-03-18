package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.request.PaymentRequest;
import pl.galushop.GaluShop.dto.response.PaymentResponse;
import pl.galushop.GaluShop.entity.Payment;
import pl.galushop.GaluShop.service.PaymentService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentsController {
    private final PaymentService paymentService;

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> showPayment(@PathVariable Long id){
        return ResponseEntity.ok(paymentService.getPaymentResponseById(id));
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> savePayment(@RequestBody PaymentRequest paymentRequest){
        PaymentResponse savedPayment = paymentService.savePayment(paymentRequest);
        return ResponseEntity.created(URI.create("/payments/" + savedPayment.paymentId()))
                .body(savedPayment);
    }

    @PutMapping
    public ResponseEntity<PaymentResponse> updatePayment(@RequestBody PaymentRequest paymentRequest){
        return ResponseEntity.ok(paymentService.updatePayment(paymentRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id){
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
