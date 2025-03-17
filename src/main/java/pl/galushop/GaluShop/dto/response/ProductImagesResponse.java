package pl.galushop.GaluShop.dto.response;

import pl.galushop.GaluShop.entity.ProductImages;

public record ProductImagesResponse(Long imagesId, String imgSrc, String altImg, Long productId) {
    public static ProductImagesResponse fromEntity(ProductImages productImages){
        return new ProductImagesResponse(
                productImages.getImagesId(),
                productImages.getImgSrc(),
                productImages.getAltImg(),
                productImages.getProduct().getProductId()
        );
    }
}
