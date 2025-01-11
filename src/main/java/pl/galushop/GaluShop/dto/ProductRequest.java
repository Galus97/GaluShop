package pl.galushop.GaluShop.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductRequest {
    private String productName;
    private String category;
    private Double price;
    private Integer categoryId;
    private String description;
    private List<ProductImageRequest> productImages;
}

