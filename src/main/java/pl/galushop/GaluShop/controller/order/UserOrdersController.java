package pl.galushop.GaluShop.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserOrdersController {
    private final OrderService orderService;

    @GetMapping("/allUserOrders")
    public List<Order> showAllUserOrders(){
        return orderService.getAllOrdersByUser(1L);
    }
}
