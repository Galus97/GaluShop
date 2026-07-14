package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.galushop.GaluShop.dto.request.ProductImageRequest;
import pl.galushop.GaluShop.dto.request.ProductRequest;
import pl.galushop.GaluShop.dto.response.ProductImagesResponse;
import pl.galushop.GaluShop.dto.response.ProductResponse;
import pl.galushop.GaluShop.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductFacadeServiceTest {
    @Mock
    private ProductService productService;
    @Mock
    private ProductImagesService productImagesService;
    @InjectMocks
    private ProductFacadeService productFacadeService;

    private Product product;
    private ProductRequest productRequest;
    private ProductImageRequest productImageRequest;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId(1L);
        product.setOrderProducts(new ArrayList<>());

        productImageRequest = ProductImageRequest.builder().build();
        productRequest = ProductRequest.builder()
                .productImages(Collections.singletonList(productImageRequest))
                .build();
    }

    @Test
    void givenCorrectRequest_whenSaveProductWithImages_thenReturnsProductResponse() {
        //given
        when(productService.saveProductEntity(any(ProductRequest.class))).thenReturn(product);
        //when
        ProductResponse response = productFacadeService.saveProductWithImages(productRequest);
        //then
        assertNotNull(response);
        assertEquals(1L, response.productId());
        verify(productImagesService, times(1)).saveProductImages(any(ProductImageRequest.class));
    }

    @Test
    void givenCorrectId_whenGetAllImagesByProductId_thenReturnsProductImagesResponseList() {
        //given
        Long productId = 1L;
        List<ProductImagesResponse> images = Collections.singletonList(
                new ProductImagesResponse(1L, "Imgage Src", "Image Alt", 1L));

        when(productImagesService.getAllImagesByProductId(productId)).thenReturn(images);
        //when
        List<ProductImagesResponse> result = productFacadeService.getAllImagesByProductId(productId);
        //then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productService, times(1)).getProductEntity(productId);
        verify(productImagesService, times(1)).getAllImagesByProductId(productId);
    }
}