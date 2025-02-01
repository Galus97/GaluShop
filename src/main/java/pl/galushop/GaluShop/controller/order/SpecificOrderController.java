package pl.galushop.GaluShop.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.service.OrderService;

@RestController
@RequiredArgsConstructor
public class SpecificOrderController {
    private final OrderService orderService;


    @GetMapping("/specificOrder")
    public Order showSpecificOrder(){
        return orderService.showSpecificOrder(1L);
    }
}
