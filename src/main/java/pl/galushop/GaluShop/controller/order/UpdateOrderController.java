package pl.galushop.GaluShop.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.OrderRequest;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.service.OrderService;
import pl.galushop.GaluShop.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UpdateOrderController {
    private final OrderService orderService;
    private final ProductService productService;

    @GetMapping("/updateOrder")
    public String updateOrder(@RequestBody OrderRequest orderRequest){
        List<Product> products = orderRequest.getProductIds().stream()
                .map(productService::findProductById)
                .collect(Collectors.toList());

        orderService.updateOrder(
                orderRequest.getUserId(),
                orderRequest.getLocalDateTime(),
                orderRequest.getOrderStatus(),
                products
        );
        return "Success";
    }

}
