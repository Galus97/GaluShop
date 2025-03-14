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
import pl.galushop.GaluShop.dto.OrderRequest;
import pl.galushop.GaluShop.dto.OrderResponse;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.service.OrderService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> showOrder(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrderResponse(id));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> saveOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponse savedOrder = orderService.saveOrder(orderRequest);
        return ResponseEntity.created(URI.create("/order/" + savedOrder.orderId()))
                .body(savedOrder);
    }

    @PutMapping
    public ResponseEntity<OrderResponse> updateOrder(@RequestBody OrderRequest orderRequest) {
        orderService.updateOrder(orderRequest);
        return ResponseEntity.ok(orderService.getOrderResponse(orderRequest.getOrderId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
