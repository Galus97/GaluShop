package pl.galushop.GaluShop.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class SpecificOrderController {
    private final OrderService orderService;

    @GetMapping("/show/{id}")
    public ResponseEntity<Order> showSpecificOrder(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrder(id));
    }
}
