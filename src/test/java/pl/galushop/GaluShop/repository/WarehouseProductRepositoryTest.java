package pl.galushop.GaluShop.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.entity.WarehouseProduct;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WarehouseProductRepositoryTest {

    @Autowired
    TestEntityManager entityManager;
    @Autowired
    WarehouseProductRepository warehouseProductRepository;
    private WarehouseProduct warehouseProduct;

    @BeforeEach
    void setUp(){
        Product persistedProduct = entityManager.persistAndFlush(Product.builder()
                .productName("Phone")
                .description("Valid description of phone")
                .price(550d)
                .category("Category 1")
                .categoryId(1)
                .build());
        warehouseProduct = WarehouseProduct.builder()
                .product(persistedProduct)
                .quantity(10)
                .build();
        entityManager.persistAndFlush(warehouseProduct);
    }

    @Test
    void givenExistingProductId_whenFindByProductId_thenReturnWarehouseProduct(){
        //when
        Optional<WarehouseProduct> optionalWarehouseProduct =
                warehouseProductRepository.findByProduct_ProductId(warehouseProduct.getProduct().getProductId());
        //then
        assertEquals(warehouseProduct, optionalWarehouseProduct.get());
    }

    @Test
    void givenNonExistentProductId_whenFindByProductId_thenReturnEmptyOptional(){
        //when
        Optional<WarehouseProduct> optionalWarehouseProduct =
                warehouseProductRepository.findByProduct_ProductId(9999L);
        //then
        assertFalse(optionalWarehouseProduct.isPresent());
    }

    @Test
    void givenInvalidProductId_whenFindByProductId_thenReturnEmptyOptional(){
        //when
        Optional<WarehouseProduct> optionalWarehouseProduct =
                warehouseProductRepository.findByProduct_ProductId(-1L);
        //then
        assertFalse(optionalWarehouseProduct.isPresent());
    }

    @Test
    void givenNullProductId_whenFindByProductId_thenReturnEmptyOptional(){
        //when
        Optional<WarehouseProduct> optionalWarehouseProduct =
                warehouseProductRepository.findByProduct_ProductId(null);
        //then
        assertFalse(optionalWarehouseProduct.isPresent());
    }
}