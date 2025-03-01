package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.OrderRequest;
import pl.galushop.GaluShop.dto.ProductQuantityRequest;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.OrderProduct;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.exception.OrderNotFoundException;
import pl.galushop.GaluShop.exception.ProductNotFoundException;
import pl.galushop.GaluShop.exception.UserNotFoundException;
import pl.galushop.GaluShop.repository.OrderRepository;
import pl.galushop.GaluShop.repository.ProductRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class responsible for managing order operations.
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final MessageService messageService;
    private final ProductService productService;

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId The ID of the order to retrieve.
     * @return The retrieved order entity.
     * @throws IllegalArgumentException if the order ID is null or invalid.
     * @throws OrderNotFoundException if no order is found with the given ID.
     */
    public Order getOrder(Long orderId){
        if(orderId == null || orderId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidOrderId", orderId));
        }
        return orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException(messageService.getMessage("error.orderNotFound", orderId)));
    }

    /**
     * Saves a new order to the database.
     *
     * @param orderRequest The request object containing order details.
     * @throws UserNotFoundException if the user associated with the order is not found.
     * @throws ProductNotFoundException if any product in the order is not found.
     */
    public void saveOrder(OrderRequest orderRequest) {
        Order order = buildOrder(orderRequest);
        orderRepository.save(order);
    }

    /**
     * Retrieves all orders made by a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of orders associated with the user.
     * @throws IllegalArgumentException if the user ID is null or invalid.
     * @throws UserNotFoundException if the user is not found.
     * @throws OrderNotFoundException if no orders are found for the user.
     */
    public List<Order> getAllOrdersByUser(Long userId) {
        if (userId == null || userId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidUserId", userId));
        }
        //Throws exception if user doesn't exist in database
        userService.getUser(userId);

        return orderRepository.findAllByUser_UserId(userId).orElseThrow(
                () -> new OrderNotFoundException(messageService.getMessage("error.orderNotFoundByUserId", userId)));
    }

    /**
     * Retrieves an order by the associated user ID.
     *
     * @param userId The ID of the user.
     * @return The retrieved order entity.
     * @throws IllegalArgumentException if the user ID is null or invalid.
     * @throws UserNotFoundException if the user is not found.
     * @throws OrderNotFoundException if no order is found for the user.
     */
    public Order getOrderByUserId(Long userId) {
        if (userId == null || userId < 0) {
            throw new IllegalArgumentException(messageService.getMessage("error.orderNotFoundByUserId", userId));
        }
        //Throws exception if user doesn't exist in database
        userService.getUser(userId);

        return orderRepository.findByUser_UserId(userId).orElseThrow(
                () -> new OrderNotFoundException(messageService.getMessage("error.orderNotFoundByUserId", userId)));

    }

    /**
     * Deletes an order by its ID.
     *
     * @param orderId The ID of the order to delete.
     * @throws IllegalArgumentException if the order ID is null or invalid.
     * @throws OrderNotFoundException if no order is found with the given ID.
     */
    public void deleteOrder(Long orderId){
        if(orderId == null || orderId < 0) {
            throw new IllegalArgumentException(messageService.getMessage("error.invalidOrderId", orderId));
        }
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException(messageService.getMessage("error.orderNotFound", orderId)));
        orderRepository.delete(order);
    }

    /**
     * Updates an existing order's details.
     *
     * @param orderRequest The request object containing updated order details.
     * @throws IllegalArgumentException if the request object contains an invalid order ID.
     * @throws OrderNotFoundException if no order is found with the given ID.
     * @throws UserNotFoundException if the user associated with the order is not found.
     * @throws ProductNotFoundException if any product in the order is not found.
     */
    public void updateOrder(OrderRequest orderRequest){
        if(orderRequest.getOrderId() == null || orderRequest.getOrderId() < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidOrderId", orderRequest.getOrderId()));
        }
        Order existingOrder = orderRepository.findById(orderRequest.getOrderId()).orElseThrow(
                () -> new OrderNotFoundException(messageService.getMessage("error.orderNotFound", orderRequest.getOrderId())));
        Order updatedOrder = buildOrder(orderRequest);
        updatedOrder.setOrderId(existingOrder.getOrderId());
        orderRepository.save(updatedOrder);
    }

    /**
     * Builds an Order entity from the given OrderRequest.
     *
     * @param orderRequest The request object containing order details.
     * @return The constructed Order entity.
     * @throws UserNotFoundException if the user associated with the order is not found.
     * @throws ProductNotFoundException if any product in the order is not found.
     */
    private Order buildOrder (OrderRequest orderRequest){
        User user = userService.getUser(orderRequest.getUserId());

        List<Long> productIds = orderRequest.getProductQuantityRequests().stream()
                .map(ProductQuantityRequest::getProductId).toList();

        Map<Long, Product> productsMap = productService.getAllProductByIds(productIds).stream()
                .collect(Collectors.toMap(Product::getProductId, product -> product));

        List<OrderProduct> orderProducts = orderRequest.getProductQuantityRequests().stream()
                .map(pq -> {
                    Product product = productsMap.get(pq.getProductId());
                    if(product == null){
                        throw new ProductNotFoundException(messageService.getMessage("error.productNotFound", pq.getProductId()));
                    }
                    return OrderProduct.builder()
                            .order(null)
                            .product(product)
                            .quantity(pq.getQuantity())
                            .build();
                }).toList();

        Order order = Order.builder()
                .localDateTime(orderRequest.getLocalDateTime())
                .status(orderRequest.getOrderStatus())
                .user(user)
                .orderProducts(orderProducts)
                .build();

        orderProducts.forEach(op -> op.setOrder(order));

        return order;
    }
}
