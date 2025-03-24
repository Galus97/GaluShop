package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.entity.OrderProduct;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.*;

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
}