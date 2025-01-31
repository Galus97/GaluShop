package pl.galushop.GaluShop.dto;

import lombok.Data;
import pl.galushop.GaluShop.entity.Product;

@Data
public class ProductImageRequest {
    private Long imagesId;
    private String imgSrc;
    private String altImg;
    private Product product;
}
