package pl.galushop.GaluShop.dto.response;

public record OrderProductResponse(Long orderId, Long productId, Integer quantity) {
}
