package pl.galushop.GaluShop.dto.request;

import lombok.Builder;
import lombok.Data;
import pl.galushop.GaluShop.entity.Product;

@Data
@Builder
public class ProductImageRequest {
    private Long imagesId;
    private String imgSrc;
    private String altImg;
    private Product product;
}
