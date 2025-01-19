package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserOrdersController {
    private final OrderService orderService;

    @PostMapping("/allUserOrders")
    public List<Order> showAllUserOrders(){
        return orderService.getAllOrdersByUser(1L);
    }
}
