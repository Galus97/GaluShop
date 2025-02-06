package pl.galushop.GaluShop.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.OrderRequest;
import pl.galushop.GaluShop.service.OrderService;

@RestController
@RequiredArgsConstructor
public class DeleteOrderController {
    private final OrderService orderService;

    @GetMapping("/deleteOrder")
    public String deleteOrder(@RequestBody OrderRequest orderRequest){
        orderService.deleteOrder(orderRequest.getUserId());
        return "Success";
    }
}
