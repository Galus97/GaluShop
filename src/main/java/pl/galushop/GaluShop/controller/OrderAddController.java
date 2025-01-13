package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.OrderRequest;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.service.OrderService;
import pl.galushop.GaluShop.service.ProductService;
import pl.galushop.GaluShop.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderAddController {

    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;

    @PostMapping("/addOrder")
    public String saveNewOrder(@RequestBody OrderRequest orderRequest) {

        Optional<User> user = userService.getUserById(orderRequest.getUserId());

        if(user.isEmpty()){
            return "User not found";
        }

        List<Product> products = orderRequest.getProductIds().stream()
                .map(productService::findProductById)
                .collect(Collectors.toList());

        Order order = new Order();
        order.setUser(user.get());
        order.setProducts(products);

        orderService.saveOrderToDatabase(order);
        return "Order added successfully!";
    }
}
