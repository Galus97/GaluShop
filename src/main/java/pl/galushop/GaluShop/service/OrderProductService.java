package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.OrderProduct;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.exception.OrderNotFoundException;
import pl.galushop.GaluShop.exception.ProductNotFoundException;
import pl.galushop.GaluShop.repository.OrderProductRepository;
import pl.galushop.GaluShop.repository.OrderRepository;
import pl.galushop.GaluShop.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderProductService {
    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public void saveOrderProduct(Long orderId, Long productId, int quantity){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(""));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(""));
        OrderProduct orderProduct = new OrderProduct(order, product, quantity);
        orderProductRepository.save(orderProduct);
    }

    public List<OrderProduct> getOrderProductByOrderId(Long orderId){
        if(orderId == null || orderId < 0){
            throw new IllegalArgumentException("");
        }
        return orderProductRepository.findByOrderOrderId(orderId);
    }
}
