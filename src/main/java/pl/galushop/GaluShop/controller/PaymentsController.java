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
import pl.galushop.GaluShop.dto.PaymentsRequest;
import pl.galushop.GaluShop.entity.Payments;
import pl.galushop.GaluShop.service.PaymentsService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentsController {
    private final PaymentsService paymentsService;

    @GetMapping("/{id}")
    public ResponseEntity<Payments> showPayment(@PathVariable Long id){
        return ResponseEntity.ok(paymentsService.getPaymentById(id));
    }

    @PostMapping
    public ResponseEntity<Payments> savePayment(@RequestBody PaymentsRequest paymentsRequest){
        Payments savedPayment = paymentsService.savePayment(paymentsRequest);
        return ResponseEntity.created(URI.create("/payments/" + savedPayment.getPaymentId()))
                .body(savedPayment);
    }

    @PutMapping
    public ResponseEntity<Payments> updatePayment(@RequestBody PaymentsRequest paymentsRequest){
        paymentsService.updatePayment(paymentsRequest);
        return ResponseEntity.ok(paymentsService.getPaymentById(paymentsRequest.getPaymentsId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id){
        paymentsService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
