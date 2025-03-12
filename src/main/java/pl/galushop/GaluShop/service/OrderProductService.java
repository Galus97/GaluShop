package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.component.ErrorMessages;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.OrderProductDto;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.OrderProduct;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.exception.OrderNotFoundException;
import pl.galushop.GaluShop.exception.ProductNotFoundException;
import pl.galushop.GaluShop.repository.OrderProductRepository;
import pl.galushop.GaluShop.repository.OrderRepository;
import pl.galushop.GaluShop.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for managing operations related to order products.
 */
@Service
@RequiredArgsConstructor
public class OrderProductService {
    private final OrderProductRepository orderProductRepository;
    private final MessageService messageService;

    /**
     * Saves an order product entity to the database.
     *
     * @param orderProduct The order product entity to save.
     * @throws IllegalArgumentException if the provided orderProduct is null.
     */
    public void saveOrderProduct(OrderProduct orderProduct){
        throwIfObjectIsNull(orderProduct);

        orderProductRepository.save(orderProduct);
    }

    /**
     * Retrieves all products associated with a specific order.
     *
     * @param orderId The ID of the order.
     * @return A list of DTO order product entities associated with the order.
     * @throws IllegalArgumentException if the order ID is null or invalid.
     */
    public List<OrderProductDto> getOrderProductsByOrderId(Long orderId){
        throwIfIdIsInvalid(orderId, ErrorMessages.INVALID_ORDER_ID);
        return orderProductRepository.findByOrder_OrderId(orderId)
                .stream()
                .map(OrderProductDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all orders associated with a specific product.
     *
     * @param productId The ID of the product.
     * @return A list of DTO order product entities associated with the order.
     * @throws IllegalArgumentException if the order ID is null or invalid.
     */
    public List<OrderProductDto> getOrderProductsByProductId(Long productId){
        throwIfIdIsInvalid(productId, ErrorMessages.INVALID_PRODUCT_ID);
        return orderProductRepository.findByProduct_ProductId(productId)
                .stream()
                .map(OrderProductDto::fromEntity)
                .collect(Collectors.toList());
    }

    private void throwIfIdIsInvalid(Long id, String message){
        if(id == null || id <= 0){
            throw new IllegalArgumentException(messageService.getMessage(message, id));
        }
    }

    private void throwIfObjectIsNull(OrderProduct orderProduct){
        if(orderProduct == null){
            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.ORDER_PRODUCT_IS_NULL));
        }
    }
}
