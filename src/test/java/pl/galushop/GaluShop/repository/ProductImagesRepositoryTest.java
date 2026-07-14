package pl.galushop.GaluShop.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pl.galushop.GaluShop.model.Product;
import pl.galushop.GaluShop.model.ProductImages;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductImagesRepositoryTest {

    @Autowired
    TestEntityManager entityManager;
    @Autowired
    ProductImagesRepository productImagesRepository;

    private ProductImages productImages;

    @BeforeEach
    void setUp() {
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

    @Test
    void givenExistingProductId_whenFindAllByProductId_thenReturnProductImagesList() {
        //when
        List<ProductImages> productImagesList =
                productImagesRepository.findAllByProduct_ProductId(productImages.getProduct().getProductId());
        //then
        assertThat(productImagesList)
                .hasSize(1)
                .contains(productImages);
    }

    @Test
    void givenNonExistentProductId_whenFindAllByProductId_thenReturnEmptyList() {
        //when
        List<ProductImages> productImagesList =
                productImagesRepository.findAllByProduct_ProductId(9999L);
        //then
        assertThat(productImagesList).isEmpty();
    }

    @Test
    void givenInvalidProductId_whenFindAllByProductId_thenReturnEmptyList() {
        //when
        List<ProductImages> productImagesList =
                productImagesRepository.findAllByProduct_ProductId(-1L);
        //then
        assertThat(productImagesList).isEmpty();
    }

    @Test
    void givenNullProductId_whenFindAllByProductId_thenReturnEmptyList() {
        //when
        List<ProductImages> productImagesList =
                productImagesRepository.findAllByProduct_ProductId(null);
        //then
        assertThat(productImagesList).isEmpty();
    }
}