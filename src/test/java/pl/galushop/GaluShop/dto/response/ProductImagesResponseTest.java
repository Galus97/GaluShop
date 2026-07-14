package pl.galushop.GaluShop.dto.response;

import org.junit.jupiter.api.Test;
import pl.galushop.GaluShop.model.Product;
import pl.galushop.GaluShop.model.ProductImages;

import static org.junit.jupiter.api.Assertions.*;

class ProductImagesResponseTest {

    @Test
    void givenProductImages_whenFromEntity_thenReturnsCorrectResponse(){
        //given
        Product product = new Product();
        product.setProductId(1L);

        ProductImages productImages = ProductImages.builder()
                .imagesId(1L)
                .imgSrc("Img src")
                .altImg("Img alt")
                .product(product)
                .build();
        //when
        ProductImagesResponse response = ProductImagesResponse.fromEntity(productImages);
        //then
        assertEquals(1L, response.imagesId());
        assertEquals("Img src", response.imgSrc());
        assertEquals("Img alt", response.altImg());
        assertEquals(1L, response.productId());
    }
}