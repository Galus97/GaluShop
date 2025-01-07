package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.service.OrderService;
import pl.galushop.GaluShop.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderAddController {

    private final OrderService orderService;
    private final ProductService productService;

    @GetMapping("/addOrder")
    public String getOrderAdd(){
        Order order = new Order();

        Product product = productService.findProductById(1L);

        List<Product> products = new ArrayList<>();
        products.add(product);

        order.setProducts(products);

        orderService.saveOrderToDatabase(order);

        return "success";
    }
}
