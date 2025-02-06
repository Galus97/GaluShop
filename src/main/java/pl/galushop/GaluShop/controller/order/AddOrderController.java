package pl.galushop.GaluShop.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.OrderRequest;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.service.OrderService;
import pl.galushop.GaluShop.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AddOrderController {
    private final OrderService orderService;
    private final ProductService productService;

    @PostMapping("/addOrder")
    public String saveNewOrder(@RequestBody OrderRequest orderRequest) {
        List<Product> products = orderRequest.getProductIds().stream()
                .map(productService::findProductById)
                .collect(Collectors.toList());

        Order order = new Order();
        order.setLocalDateTime(orderRequest.getLocalDateTime());
        order.setStatus(orderRequest.getOrderStatus());
        order.setUser(order.getUser());
        order.setProducts(products);

        orderService.saveOrderToDatabase(order);
        return "Order added successfully!";
    }
}
