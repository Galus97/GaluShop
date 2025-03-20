package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.entity.WarehouseProduct;
import pl.galushop.GaluShop.repository.WarehouseProductRepository;

import static org.junit.jupiter.api.Assertions.*;

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
}