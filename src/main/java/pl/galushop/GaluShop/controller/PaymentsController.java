package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.entity.Payments;
import pl.galushop.GaluShop.service.PaymentsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentsController {
    private final PaymentsService paymentsService;

    @GetMapping("/{id}")
    public ResponseEntity<Payments> showPayments(@PathVariable Long id){
        return ResponseEntity.ok(paymentsService.getPaymentById(id));
    }


}
