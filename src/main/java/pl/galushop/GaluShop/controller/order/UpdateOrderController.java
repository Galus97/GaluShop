package pl.galushop.GaluShop.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.OrderRequest;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class UpdateOrderController {
    private final OrderService orderService;

    @PutMapping("/update")
    public ResponseEntity<Order> updateOrder(@RequestBody OrderRequest orderRequest) {
        orderService.updateOrder(orderRequest);
        return ResponseEntity.ok(orderService.getOrder(orderRequest.getOrderId()));
    }

}
