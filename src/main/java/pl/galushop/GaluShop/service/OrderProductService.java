package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.ServiceValidator;
import pl.galushop.GaluShop.component.ErrorMessages;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.response.OrderProductResponse;
import pl.galushop.GaluShop.entity.OrderProduct;
import pl.galushop.GaluShop.repository.OrderProductRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for managing order product operations.
 * It handles creating and retrieving order-product relationships.
 */
@Service
@RequiredArgsConstructor
public class OrderProductService {
    private final OrderProductRepository orderProductRepository;
    private final MessageService messageService;
    private final ServiceValidator serviceValidator;

    /**
     * Saves an {@link OrderProduct} entity to the database.
     *
     * @param orderProduct The {@link OrderProduct} entity to be saved.
     * @return A response DTO representing the saved order product.
     * @throws IllegalArgumentException If the orderProduct is {@code null}.
     */
    public OrderProductResponse saveOrderProduct(OrderProduct orderProduct) {
        serviceValidator.throwIfRequestIsNull(orderProduct, ErrorMessages.ORDER_PRODUCT_IS_NULL);
        return OrderProductResponse.fromEntity(orderProductRepository.save(orderProduct));
    }

    /**
     * Retrieves all order products associated with a specific order ID.
     *
     * @param orderId The ID of the order.
     * @return A list of response DTOs representing the products in the order.
     * @throws IllegalArgumentException If the order ID is {@code null} or invalid.
     */
    public List<OrderProductResponse> getOrderProductsByOrderId(Long orderId) {
        throwIfIdIsInvalid(orderId, ErrorMessages.INVALID_ORDER_ID);
        return orderProductRepository.findByOrder_OrderId(orderId)
                .stream()
                .map(OrderProductResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all order products associated with a specific product ID.
     *
     * @param productId The ID of the product.
     * @return A list of response DTOs representing the orders containing the product.
     * @throws IllegalArgumentException If the product ID is {@code null} or invalid.
     */
    public List<OrderProductResponse> getOrderProductsByProductId(Long productId) {
        throwIfIdIsInvalid(productId, ErrorMessages.INVALID_PRODUCT_ID);
        return orderProductRepository.findByProduct_ProductId(productId)
                .stream()
                .map(OrderProductResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
