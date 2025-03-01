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

/**
 * Service class responsible for managing operations related to order products.
 */
@Service
@RequiredArgsConstructor
public class OrderProductService {
    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final MessageService messageService;

    /**
     * Saves a new association between an order and a product.
     *
     * @param orderId   The ID of the order.
     * @param productId The ID of the product.
     * @param quantity  The quantity of the product in the order.
     * @throws OrderNotFoundException  if no order is found with the given ID.
     * @throws ProductNotFoundException if no product is found with the given ID.
     */
    public void saveOrderProduct(Long orderId, Long productId, int quantity){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(messageService.getMessage("error.orderNotFound")));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(messageService.getMessage("error.productNotFound")));
        OrderProduct orderProduct = new OrderProduct(order, product, quantity);
        orderProductRepository.save(orderProduct);
    }

    /**
     * Saves an order product entity to the database.
     *
     * @param orderProduct The order product entity to save.
     * @throws IllegalArgumentException if the provided orderProduct is null.
     */
    public void saveOrderProduct(OrderProduct orderProduct){
        if(orderProduct == null){
            throw new IllegalArgumentException(messageService.getMessage("error.orderProductIsNull"));
        }
        orderProductRepository.save(orderProduct);
    }

    /**
     * Retrieves all products associated with a specific order.
     *
     * @param orderId The ID of the order.
     * @return A list of order product entities associated with the order.
     * @throws IllegalArgumentException if the order ID is null or invalid.
     */
    public List<OrderProduct> getOrderProductsByOrderId(Long orderId){
        if(orderId == null || orderId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidOrderId", orderId));
        }
        return orderProductRepository.findByOrder_OrderId(orderId);
    }
}
