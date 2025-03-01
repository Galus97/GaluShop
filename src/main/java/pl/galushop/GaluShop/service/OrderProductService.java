package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.component.MessageService;
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
    private final MessageService messageService;

    public void saveOrderProduct(Long orderId, Long productId, int quantity){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(messageService.getMessage("error.orderNotFound")));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(messageService.getMessage("error.productNotFound")));
        OrderProduct orderProduct = new OrderProduct(order, product, quantity);
        orderProductRepository.save(orderProduct);
    }

    public void saveOrderProduct(OrderProduct orderProduct){
        if(orderProduct != null){
            orderProductRepository.save(orderProduct);
        }
    }

    public List<OrderProduct> getOrderProductsByOrderId(Long orderId){
        if(orderId == null || orderId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidOrderId", orderId));
        }
        return orderProductRepository.findByOrder_OrderId(orderId);
    }
}
