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
import pl.galushop.GaluShop.dto.request.NewOrderRequest;
import pl.galushop.GaluShop.dto.response.OrderResponse;
import pl.galushop.GaluShop.service.OrderService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> showOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderResponse(id));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> saveOrder(@RequestBody NewOrderRequest newOrderRequest) {
        OrderResponse savedOrder = orderService.saveOrder(newOrderRequest);
        return ResponseEntity.created(URI.create("/order/" + savedOrder.orderId()))
                .body(savedOrder);
    }

    @PutMapping
    public ResponseEntity<OrderResponse> updateOrder(@RequestBody NewOrderRequest newOrderRequest) {
        return ResponseEntity.ok(orderService.updateOrder(newOrderRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<OrderResponse>> showAllUserOrders(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getAllOrdersByUser(id));
    }
}
