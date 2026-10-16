package pl.galushop.GaluShop.dto.response;

import pl.galushop.GaluShop.model.Product;

import java.util.List;

public record ProductResponse(Long productId, String productName, String description, Double price,
                              String category, Integer categoryId, List<OrderItemResponse> products) {

    public static ProductResponse fromEntity(Product product) {
        return new ProductResponse(
                product.getProductId(),
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory(),
                product.getCategoryId(),
                product.getOrderItems().stream().map(OrderItemResponse::fromEntity).toList()
        );
    }
}
