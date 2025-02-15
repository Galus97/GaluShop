package pl.galushop.GaluShop.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.OrderRequest;
import pl.galushop.GaluShop.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order/")
public class SaveOrderController {
    private final OrderService orderService;

    @PutMapping("/save")
    public String saveNewOrder(@RequestBody OrderRequest orderRequest) {
        orderService.saveOrderToDatabase(orderRequest);
        return "Order added successfully!";
    }
    // Dokończyć
}
