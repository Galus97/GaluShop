package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.entity.ProductImages;
import pl.galushop.GaluShop.repository.ProductImagesRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductImagesServiceTest {
    @Mock
    ProductImagesRepository repository;
    @Mock
    MessageService messageService;
    @InjectMocks
    ProductImagesService service;
    private ProductImages productImages;

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
    }
}