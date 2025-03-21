package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.request.WarehouseProductRequest;
import pl.galushop.GaluShop.dto.response.WarehouseProductResponse;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.entity.WarehouseProduct;
import pl.galushop.GaluShop.exception.WarehouseProductNotFoundException;
import pl.galushop.GaluShop.repository.WarehouseProductRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WarehouseProductServiceTest {
    @Mock
    WarehouseProductRepository repository;
    @Mock
    ProductService productService;
    @Mock
    MessageService messageService;
    @InjectMocks
    WarehouseProductService service;
    private WarehouseProduct warehouseProduct;
    @BeforeEach
    void setUp(){
        warehouseProduct = WarehouseProduct.builder()
                .warehouseProductId(1L)
                .product(new Product())
                .quantity(10)
                .build();
    }

    @Test
    void givenExistingId_whenGetWarehouseProductEntity_thenReturnsWarehouseProduct(){
        //given
        when(repository.findById(1L)).thenReturn(Optional.of(warehouseProduct));
        //when
        WarehouseProduct foundWarehouseProduct = service.getWarehouseProductEntity(1L);
        //then
        assertNotNull(foundWarehouseProduct);
        assertEquals(1L, foundWarehouseProduct.getWarehouseProductId());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void givenNonExistentId_whenGetWarehouseProductEntity_thenThrowsException(){
        //given
        when(repository.findById(any())).thenReturn(Optional.empty());
        //then
        assertThrows(WarehouseProductNotFoundException.class, () -> service.getWarehouseProductEntity(1L));
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void givenInvalidId_whenGetWarehouseProductEntity_thenThrowsException(){
        //then
        assertThrows(IllegalArgumentException.class, () -> service.getWarehouseProductEntity(-1L));
        verify(repository, times(0)).findById(any());
    }

    @Test
    void givenNullId_whenGetWarehouseProductEntity_thenThrowsException(){
        //then
        assertThrows(IllegalArgumentException.class, () -> service.getWarehouseProductEntity(null));
        verify(repository, times(0)).findById(any());
    }

    @Test
    void givenExistingId_whenGetWarehouseProductResponse_thenReturnWarehouseProductResponse(){
        //given
        when(repository.findById(1L)).thenReturn(Optional.of(warehouseProduct));
        //when
        WarehouseProductResponse warehouseProductResponse = service.getWarehouseProductResponse(1L);
        //then
        assertNotNull(warehouseProductResponse);
        assertEquals(1L, warehouseProductResponse.warehouseProductId());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void givenNonExistentId_whenGetWarehouseProductResponse_thenThrowsException(){
        //given
        when(repository.findById(any())).thenReturn(Optional.empty());
        //then
        assertThrows(WarehouseProductNotFoundException.class, () -> service.getWarehouseProductResponse(1L));
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void givenCorrectRequest_whenSaveWarehouseProduct_thenReturnsWarehouseProductResponse(){
        //given
        WarehouseProductRequest request = new WarehouseProductRequest();
        request.setProductId(1L);
        request.setProductId(1L);
        request.setQuantity(10);
        when(repository.save(warehouseProduct)).thenReturn(warehouseProduct);
        //when
        WarehouseProductResponse warehouseProductResponse = service.saveWarehouseProduct(request);
        //then
        assertNotNull(warehouseProductResponse);
        assertEquals(warehouseProduct.getWarehouseProductId(), warehouseProductResponse.warehouseProductId());
        assertEquals(warehouseProduct.getQuantity(), warehouseProductResponse.quantity());
        verify(repository, times(1)).save(warehouseProduct);
    }

    @Test
    void givenInvalidProductId_whenSaveWarehouseProduct_thenThrowsException(){
        //given
        WarehouseProductRequest request = new WarehouseProductRequest();
        request.setProductId(1L);
        request.setProductId(0L); //Incorrect value
        request.setQuantity(1); //Incorrect value
        //then
        assertThrows(IllegalArgumentException.class, () -> service.saveWarehouseProduct(request));
        verify(repository, times(0)).save(warehouseProduct);
    }

    @Test
    void givenInvalidQuantity_whenSaveWarehouseProduct_thenThrowsException(){
        //given
        WarehouseProductRequest request = new WarehouseProductRequest();
        request.setProductId(1L);
        request.setProductId(1L);
        request.setQuantity(-1); //Incorrect value
        //then
        assertThrows(IllegalArgumentException.class, () -> service.saveWarehouseProduct(request));
        verify(repository, times(0)).save(warehouseProduct);
    }

    @Test
    void givenCorrectRequest_whenUpdateWarehouseProduct_thenReturnsWarehouseProductResponse(){
        //given
        WarehouseProductRequest request = new WarehouseProductRequest();
        request.setWarehouseProductId(1L);
        request.setProductId(33L);
        request.setQuantity(25);

        Product product = new Product();
        product.setProductId(33L);

        WarehouseProduct updatedWarehouseProduct = WarehouseProduct.builder()
                .warehouseProductId(1L)
                .product(product)
                .quantity(25)
                .build();
        when(repository.save(updatedWarehouseProduct)).thenReturn(updatedWarehouseProduct);
        when(repository.findById(33L)).thenReturn(Optional.of(warehouseProduct));
        //when
        WarehouseProductResponse warehouseProductResponse = service.updateWarehouseProduct(request);
        //then
        assertNotNull(warehouseProductResponse);
        assertEquals(1L, warehouseProductResponse.warehouseProductId());
        assertEquals(33L, warehouseProductResponse.productId());
        assertEquals(25, warehouseProductResponse.quantity());
        verify(repository, times(1)).save(updatedWarehouseProduct);
    }

    @Test
    void givenNonExistentWarehouseProduct_whenUpdateWarehouseProduct_thenThrowsException(){
        //given
        WarehouseProductRequest request = new WarehouseProductRequest();
        request.setWarehouseProductId(1L);
        request.setProductId(33L);
        request.setQuantity(25);

        when(repository.findById(any())).thenReturn(Optional.empty());

        //then
        assertThrows(WarehouseProductNotFoundException.class, () -> service.updateWarehouseProduct(request));
        verify(repository, times(1)).findById(any());
        verify(repository, times(0)).save(any());
    }

    @Test
    void givenNullRequest_whenUpdateWarehouseProduct_thenThrowsException(){
        //then
        assertThrows(IllegalArgumentException.class, () -> service.updateWarehouseProduct(null));
        verify(repository, times(0)).findById(any());
        verify(repository, times(0)).save(any());
    }

    @Test
    void givenNullProductId_whenUpdateWarehouseProduct_thenThrowsException(){
        //given
        WarehouseProductRequest request = new WarehouseProductRequest();
        request.setWarehouseProductId(1L);
        request.setProductId(null);
        request.setQuantity(25);
        //then
        assertThrows(IllegalArgumentException.class, () -> service.updateWarehouseProduct(request));
        verify(repository, times(0)).findById(any());
        verify(repository, times(0)).save(any());
    }
}


