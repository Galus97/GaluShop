package pl.galushop.GaluShop.entity;

import org.junit.jupiter.api.BeforeEach;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .productId(1L)
                .productName("Sample Product")
                .description("This is a sample product description with enough length.")
                .price(100.0)
                .category("Electronics")
                .categoryId(2)
                .orderProducts(Collections.emptyList())
                .build();
    }
}