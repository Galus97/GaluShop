package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.entity.Payment;
import pl.galushop.GaluShop.service.PaymentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class UserAllPaymentsController {
    private final PaymentService paymentService;

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Payment>> showAllUserPayments(@PathVariable Long id){
        return ResponseEntity.ok(paymentService.getAllPaymentsByUserId(id));
    }
}
