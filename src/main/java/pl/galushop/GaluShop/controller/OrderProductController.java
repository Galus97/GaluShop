package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.response.OrderProductResponse;
import pl.galushop.GaluShop.service.OrderProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderProductController {
    private final OrderProductService orderProductService;

    @GetMapping("/products/{id}")
    public ResponseEntity<List<OrderProductResponse>> showProductInOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderProductService.getOrderProductsByOrderId(id));
    }
}
