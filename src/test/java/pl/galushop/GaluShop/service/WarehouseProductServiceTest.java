package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.entity.WarehouseProduct;
import pl.galushop.GaluShop.repository.WarehouseProductRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    void givenExistingWarehouseId_whenGetWarehouseProductEntity_thenReturnsWarehouseProduct(){
        //given
        when(repository.findById(1L)).thenReturn(Optional.of(warehouseProduct));
        //when
        WarehouseProduct foundWarehouseProduct = service.getWarehouseProductEntity(1L);
        //then
        assertNotNull(foundWarehouseProduct);
        assertEquals(1L, foundWarehouseProduct.getWarehouseProductId());
        verify(repository, times(1)).findById(1L);
    }
}