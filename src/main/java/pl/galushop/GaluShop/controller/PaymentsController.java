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
import pl.galushop.GaluShop.entity.Payment;
import pl.galushop.GaluShop.service.PaymentService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentsController {
    private final PaymentService paymentService;

    @GetMapping("/{id}")
    public ResponseEntity<Payment> showPayment(@PathVariable Long id){
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @PostMapping
    public ResponseEntity<Payment> savePayment(@RequestBody PaymentRequest paymentRequest){
        Payment savedPayment = paymentService.savePayment(paymentRequest);
        return ResponseEntity.created(URI.create("/payments/" + savedPayment.getPaymentId()))
                .body(savedPayment);
    }

    @PutMapping
    public ResponseEntity<Payment> updatePayment(@RequestBody PaymentRequest paymentRequest){
        paymentService.updatePayment(paymentRequest);
        return ResponseEntity.ok(paymentService.getPaymentById(paymentRequest.getPaymentId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id){
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
