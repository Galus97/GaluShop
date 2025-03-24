package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.entity.OrderProduct;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.exception.ProductNotFoundException;
import pl.galushop.GaluShop.repository.ProductRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    ProductRepository repository;
    @Mock
    MessageService messageService;
    @InjectMocks
    ProductService service;
    private Product product;

    @BeforeEach
    void setUp(){
        product = Product.builder()
                .productId(null)
                .productName("Product name")
                .description("Description of the product")
                .price(10.0)
                .category("Electronic")
                .categoryId(1)
                .build();
    }

    @Test
    void givenExistingProductId_whenGetProductEntity_thenReturnsProductEntity(){
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.of(product));
        //when
        Product product = service.getProductEntity(1L);
        //then
        assertNotNull(product);
        assertEquals("Product name", product.getProductName());
        assertEquals("Description of the product", product.getDescription());
        assertEquals(10d, product.getPrice());
        assertEquals("Electronic", product.getCategory());
        assertEquals(1, product.getCategoryId());
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void givenNonExistentProductId_whenGetProductEntity_thenThrowsException(){
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(ProductNotFoundException.class, () -> service.getProductEntity(1L));
        verify(repository, times(1)).findById(1L);
    }
}