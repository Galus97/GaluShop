package pl.galushop.GaluShop.repository;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.entity.ProductImages;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductImagesRepositoryTest {

    @Autowired
    TestEntityManager entityManager;
    @Autowired
    ProductImagesRepository productImagesRepository;

    private ProductImages productImages;

    @BeforeEach
    void setUp(){
        Product persistedProduct = entityManager.persistAndFlush(Product.builder()
                .productName("Phone")
                .description("Valid description of phone")
                .price(550d)
                .category("Category 1")
                .categoryId(1)
                .build());

        productImages = ProductImages.builder()
                .imgSrc("imgSrc")
                .altImg("altImg")
                .product(persistedProduct)
                .build();
        entityManager.persistAndFlush(productImages);
    }
}