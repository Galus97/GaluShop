package pl.galushop.GaluShop.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.OrderRequest;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.service.OrderService;
import pl.galushop.GaluShop.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class UpdateOrderController {
    private final OrderService orderService;

    @PutMapping("/update")
    public String updateOrder(@RequestBody OrderRequest orderRequest){
    orderService.updateOrder(orderRequest);
        return "Success";
    }

}
