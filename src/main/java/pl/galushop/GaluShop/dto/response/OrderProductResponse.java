package pl.galushop.GaluShop.dto.response;

import pl.galushop.GaluShop.entity.OrderProduct;

public record OrderProductResponse(Long orderId, Long productId, Integer quantity) {

    public static OrderProductResponse fromEntity(OrderProduct orderProduct){
        return new OrderProductResponse(
          orderProduct.getOrder().getOrderId(),
          orderProduct.getProduct().getProductId(),
          orderProduct.getQuantity()
        );
    }
}
