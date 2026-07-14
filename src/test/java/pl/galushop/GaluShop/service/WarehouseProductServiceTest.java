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
import pl.galushop.GaluShop.model.Product;
import pl.galushop.GaluShop.model.WarehouseProduct;
import pl.galushop.GaluShop.exception.WarehouseProductNotFoundException;
import pl.galushop.GaluShop.repository.WarehouseProductRepository;

import java.util.Arrays;
import java.util.List;
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

    private WarehouseProductRequest request;
    @BeforeEach
    void setUp(){
        Product product = new Product();
        product.setProductId(1L);
        warehouseProduct = WarehouseProduct.builder()
                .warehouseProductId(1L)
                .product(product)
                .quantity(10)
                .build();

        request = WarehouseProductRequest.builder()
                .warehouseProductId(1L)
                .productId(1L)
                .quantity(10)
                .build();
    }

    @Test
    void givenExistingId_whenGetWarehouseProductEntity_thenReturnsWarehouseProductByProductId(){
        //given
        when(repository.findById(1L)).thenReturn(Optional.of(warehouseProduct));
        //when
        WarehouseProduct foundWarehouseProduct = service.getWarehouseProductEntityByProductId(1L);
        //then
        assertNotNull(foundWarehouseProduct);
        assertEquals(1L, foundWarehouseProduct.getWarehouseProductId());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void givenNonExistentId_whenGetWarehouseProductEntity_ByProductId_thenThrowsException(){
        //given
        when(repository.findById(any())).thenReturn(Optional.empty());
        //then
        assertThrows(WarehouseProductNotFoundException.class, () -> service.getWarehouseProductEntityByProductId(1L));
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void givenInvalidId_whenGetWarehouseProductEntity_ByProductId_thenThrowsException(){
        //then
        assertThrows(IllegalArgumentException.class, () -> service.getWarehouseProductEntityByProductId(-1L));
        verify(repository, times(0)).findById(any());
    }

    @Test
    void givenNullId_whenGetWarehouseProductEntity_ByProductId_thenThrowsException(){
        //then
        assertThrows(IllegalArgumentException.class, () -> service.getWarehouseProductEntityByProductId(null));
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
    void givenCorrectRequest_whenSaveWarehouseProduct_thenReturnsWarehouseProductResponse(){
        //given
        when(repository.save(warehouseProduct)).thenReturn(warehouseProduct);
        //when
        WarehouseProductResponse warehouseProductResponse = service.saveWarehouseProduct(request);
        //then
        assertNotNull(warehouseProductResponse);
        assertEquals(warehouseProduct.getWarehouseProductId(), warehouseProductResponse.warehouseProductId());
        assertEquals(warehouseProduct.getProduct().getProductId(), warehouseProductResponse.productId());
        assertEquals(warehouseProduct.getQuantity(), warehouseProductResponse.quantity());
        verify(repository, times(1)).save(warehouseProduct);
    }

    @Test
    void givenInvalidProductId_whenSaveWarehouseProduct_thenThrowsException(){
        //given
        request.setProductId(0L); //Incorrect value
        //then
        assertThrows(IllegalArgumentException.class, () -> service.saveWarehouseProduct(request));
        verify(repository, times(0)).save(warehouseProduct);
    }

    @Test
    void givenInvalidQuantity_whenSaveWarehouseProduct_thenThrowsException(){
        //given
        request.setQuantity(-1); //Incorrect value
        //then
        assertThrows(IllegalArgumentException.class, () -> service.saveWarehouseProduct(request));
        verify(repository, times(0)).save(warehouseProduct);
    }

    @Test
    void givenWarehouseProductList_whenGetAllProductInWarehouse_thenReturnsWarehouseProductResponseList(){
        //given
        List<WarehouseProduct> warehouseProductList = Arrays.asList(warehouseProduct);
        when(repository.findAll()).thenReturn(warehouseProductList);
        //when
        List<WarehouseProductResponse> allProductInWarehouse = service.getAllProductInWarehouse();
        //then
        assertNotNull(allProductInWarehouse);
        assertEquals(1, allProductInWarehouse.size());
        assertEquals(1, allProductInWarehouse.get(0).warehouseProductId());
        assertEquals(1L, allProductInWarehouse.get(0).productId());
        assertEquals(10, allProductInWarehouse.get(0).quantity());
        verify(repository, times(1)).findAll();
    }

    @Test
    void givenExistingWarehouseProduct_whenDeleteWarehouseProduct_thenDeletesWarehouseProduct(){
        //given
        when(repository.findById(1L)).thenReturn(Optional.of(warehouseProduct));
        //when
        service.deleteWarehouseProduct(1L);
        //then
        verify(repository, times(1)).delete(warehouseProduct);
    }

    @Test
    void givenCorrectRequest_whenUpdateWarehouseProduct_thenReturnsWarehouseProductResponse(){
        //given
        WarehouseProductRequest requestUpdate = WarehouseProductRequest.builder()
                .warehouseProductId(1L)
                .productId(33L)
                .quantity(25)
                .build();
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
        WarehouseProductResponse warehouseProductResponse = service.updateWarehouseProduct(requestUpdate);
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
        request.setProductId(null); //Invalid value
        //then
        assertThrows(IllegalArgumentException.class, () -> service.updateWarehouseProduct(request));
        verify(repository, times(0)).findById(any());
        verify(repository, times(0)).save(any());
    }

    @Test
    void givenInvalidProductId_whenUpdateWarehouseProduct_thenThrowsException(){
        //given
        request.setProductId(-2L); //Invalid value
        //then
        assertThrows(IllegalArgumentException.class, () -> service.updateWarehouseProduct(request));
        verify(repository, times(0)).findById(any());
        verify(repository, times(0)).save(any());
    }

    @Test
    void givenNullQuantity_whenUpdateWarehouseProduct_thenThrowsException(){
        //given
        request.setQuantity(null); //Invalid value
        //then
        assertThrows(IllegalArgumentException.class, () -> service.updateWarehouseProduct(request));
        verify(repository, times(0)).findById(any());
        verify(repository, times(0)).save(any());
    }

    @Test
    void givenInvalidQuantity_whenUpdateWarehouseProduct_thenThrowsException(){
        //given
        request.setQuantity(-1); //Invalid value
        //then
        assertThrows(IllegalArgumentException.class, () -> service.updateWarehouseProduct(request));
        verify(repository, times(0)).findById(any());
        verify(repository, times(0)).save(any());
    }

    @Test
    void givenCorrectValue_whenUpdateQuantityByProductId_thenReturnsWarehouseProductResponse(){
        //given
        when(repository.findById(any())).thenReturn(Optional.of(warehouseProduct));
        when(repository.save(warehouseProduct)).thenReturn(warehouseProduct);
        //when
        WarehouseProductResponse warehouseProductResponse = service.updateQuantityByProductId(1L, 30);
        //then
        assertNotNull(warehouseProductResponse);
        assertEquals(30, warehouseProductResponse.quantity());
        verify(repository, times(1)).save(warehouseProduct);
    }
}


