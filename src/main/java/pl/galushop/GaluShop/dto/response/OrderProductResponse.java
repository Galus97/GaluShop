package pl.galushop.GaluShop.dto.response;

import pl.galushop.GaluShop.model.OrderItem;

public record OrderProductResponse(Long orderId, Long productId, Integer quantity) {

    public static OrderProductResponse fromEntity(OrderItem orderItem) {
        return new OrderProductResponse(
                orderItem.getOrder().getOrderId(),
                orderItem.getProduct().getProductId(),
                orderItem.getQuantity()
        );
    }
}
