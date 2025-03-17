package pl.galushop.GaluShop.dto.response;

import pl.galushop.GaluShop.dto.OrderProductDto;
import pl.galushop.GaluShop.entity.Product;

import java.util.List;

public record ProductResponse (Long productId, String productName, String description, Double price,
                               String category, Integer categoryId, List<OrderProductDto> products){

    public static ProductResponse fromEntity(Product product){
        return new ProductResponse(
                product.getProductId(),
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory(),
                product.getCategoryId(),
                product.getOrderProducts().stream().map(OrderProductDto::fromEntity).toList()
        );
    }
}
