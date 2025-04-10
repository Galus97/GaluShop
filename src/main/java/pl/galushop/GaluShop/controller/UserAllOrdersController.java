package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.response.OrderResponse;
import pl.galushop.GaluShop.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class UserAllOrdersController {
    private final OrderService orderService;

    @GetMapping("/user/{id}")
    public ResponseEntity<List<OrderResponse>> showAllUserOrders(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getAllOrdersByUser(id));
    }
}
