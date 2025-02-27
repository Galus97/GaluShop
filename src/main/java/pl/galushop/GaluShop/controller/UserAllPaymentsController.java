package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.entity.Payments;
import pl.galushop.GaluShop.service.PaymentsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class UserAllPaymentsController {
    private final PaymentsService paymentsService;

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Payments>> showAllUserPayments(@PathVariable Long id){
        return ResponseEntity.ok(paymentsService.getAllPaymentsByUserId(id));
    }
}
