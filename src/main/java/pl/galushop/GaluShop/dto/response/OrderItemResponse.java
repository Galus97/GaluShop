package pl.galushop.GaluShop.dto.response;

import pl.galushop.GaluShop.model.OrderItem;

public record OrderItemResponse(
        Long orderId,
        Long productId,
        Integer quantity,
        Double totalCostOfProduct) {

    public static OrderItemResponse fromEntity(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getOrder().getOrderId(),
                orderItem.getProduct().getProductId(),
                orderItem.getProductQuantity(),
                orderItem.getTotalCostOfProduct()
        );
    }
}
