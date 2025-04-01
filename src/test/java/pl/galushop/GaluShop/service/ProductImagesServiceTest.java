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
import pl.galushop.GaluShop.exception.ProductImagesNotFoundException;
import pl.galushop.GaluShop.repository.ProductImagesRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void setUp() {
        Product product = new Product();
        product.setProductId(1L);
        productImages = ProductImages.builder()
                .imagesId(1L)
                .imgSrc("Image src")
                .altImg("Image alt")
                .product(product)
                .build();
        productImageRequest = ProductImageRequest.builder()
                .imagesId(1L)
                .imgSrc("Image src")
                .altImg("Image alt")
                .product(product)
                .build();
    }

    @Test
    void givenCorrectRequest_whenSaveProductImages_thenReturnsProductImagesResponse() {
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
    void givenNullRequest_whenSaveProductImages_thenThrowsException() {
        //then
        assertThrows(IllegalArgumentException.class, () -> service.saveProductImages(null));
        verify(repository, times(0)).save(productImages);
    }

    @Test
    void givenExistingId_whenGetProductImages_thenReturnsProductImagesResponse() {
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

    @Test
    void givenNonExistentId_whenGetProductImages_thenThrowsException() {
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(ProductImagesNotFoundException.class, () -> service.getProductImages(1L));
        verify(repository, times(1)).findById(anyLong());

    }

    @Test
    void givenInvalidId_whenGetProductImages_thenThrowsException() {
        //then
        assertThrows(IllegalArgumentException.class, () -> service.getProductImages(-1L));
        verify(repository, times(0)).findById(anyLong());
    }

    @Test
    void givenNullId_whenGetProductImages_thenThrowsException() {
        //then
        assertThrows(IllegalArgumentException.class, () -> service.getProductImages(null));
        verify(repository, times(0)).findById(anyLong());
    }

    @Test
    void givenCorrectRequest_whenUpdateProductImages_thenReturnsProductImagesResponse() {
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.of(productImages));
        when(repository.save(any(ProductImages.class))).thenReturn(productImages);
        //when
        ProductImagesResponse response = service.updateProductImages(productImageRequest);
        //then
        assertNotNull(response);
        assertEquals(1L, response.imagesId());
        assertEquals("Image src", response.imgSrc());
        assertEquals("Image alt", response.altImg());
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).save(productImages);
    }

    @Test
    void givenExistingId_whenDeleteProductImages_thenDeletesProductImagesResponse() {
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.of(productImages));
        //when
        service.deleteProductImages(1L);
        //then
        verify(repository, times(1)).delete(productImages);
    }

    @Test
    void givenExistingId_whenGetAllImagesByProductId_thenReturnsProductImagesResponseList() {
        //given
        List<ProductImages> productImagesList = Arrays.asList(productImages);
        when(repository.findAllByProduct_ProductId(anyLong())).thenReturn(productImagesList);
        //when
        List<ProductImagesResponse> response = service.getAllImagesByProductId(1L);
        //then
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(1L, response.get(0).imagesId());
        assertEquals("Image src", response.get(0).imgSrc());
        assertEquals("Image alt", response.get(0).altImg());
        verify(repository, times(1)).findAllByProduct_ProductId(anyLong());
    }
}