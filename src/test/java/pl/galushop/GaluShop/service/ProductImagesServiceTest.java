package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.request.ProductImageRequest;
import pl.galushop.GaluShop.dto.response.ProductImagesResponse;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.entity.ProductImages;
import pl.galushop.GaluShop.repository.ProductImagesRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductImagesServiceTest {
    @Mock
    ProductImagesRepository repository;
    @Mock
    MessageService messageService;
    @InjectMocks
    ProductImagesService service;
    private ProductImages productImages;
    private ProductImageRequest productImageRequest;

    @BeforeEach
    void setUp(){
        Product product = new Product();
        product.setProductId(1L);
        productImages = ProductImages.builder()
                .imagesId(1L)
                .imgSrc("Image src")
                .altImg("Image alt")
                .product(product)
                .build();
        productImageRequest = new ProductImageRequest();
        productImageRequest.setImagesId(1L);
        productImageRequest.setImgSrc("Image src");
        productImageRequest.setAltImg("Image alt");
        productImageRequest.setProduct(product);
    }

    @Test
    void givenCorrectRequest_whenSaveProductImages_thenReturnsProductImagesResponse(){
        //given
        when(repository.save(any(ProductImages.class))).thenReturn(productImages);
        //when
        ProductImagesResponse response = service.saveProductImages(productImageRequest);
        //then
        assertNotNull(response);
        assertEquals(1L, response.imagesId());
        assertEquals("Image src", response.imgSrc());
        assertEquals("Image alt", response.altImg());
        verify(repository, times(1)).save(productImages);
    }

    @Test
    void givenNullRequest_whenSaveProductImages_thenThrowsException(){
        //then
        assertThrows(IllegalArgumentException.class, () -> service.saveProductImages(null));
        verify(repository, times(0)).save(productImages);
    }

    @Test
    void givenExistingId_whenGetProductImages_thenReturnsProductImagesResponse(){
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.of(productImages));
        //when
        ProductImagesResponse response = service.getProductImages(1L);
        //then
        assertNotNull(response);
        assertEquals(1L, response.imagesId());
        assertEquals("Image src", response.imgSrc());
        assertEquals("Image alt", response.altImg());
        verify(repository, times(1)).findById(anyLong());
    }
}