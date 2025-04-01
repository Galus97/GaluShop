package pl.galushop.GaluShop.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductRequest {
    private Long productId;
    private String productName;
    private String category;
    private Double price;
    private Integer categoryId;
    private String description;
    private List<ProductImageRequest> productImages;
}
