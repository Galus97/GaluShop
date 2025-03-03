package pl.galushop.GaluShop.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    void shouldCreateProductUsingBuilder() {
        assertNotNull(product);
        assertNotNull(product.getProductName());
        assertNotNull(product.getDescription());
        assertNotNull(product.getCategory());
        assertNotNull(product.getOrderProducts());
        assertTrue(product.getOrderProducts().isEmpty());
    }

    @Test
    void shouldCreateProductWithNoArgsConstructor() {
        Product emptyProduct = new Product();

        assertThat(emptyProduct)
                .isNotNull()
                .satisfies(p -> {
                    assertNull(p.getProductId());
                    assertNull(p.getProductName());
                    assertNull(p.getDescription());
                    assertNull(p.getCategory());
                    assertNull(p.getOrderProducts());
                });
    }

    @Test
    void shouldCreateProductWithAllArgsConstructor() {
        Product constructedProduct = new Product(
                2L,
                "Phone",
                "High-end smartphone with powerful specs.",
                1200.0,
                "Electronics",
                3,
                Collections.emptyList()
        );

        assertThat(constructedProduct)
                .satisfies(p -> {
                    assertEquals(2L, p.getProductId());
                    assertEquals("Phone", p.getProductName());
                    assertEquals("High-end smartphone with powerful specs.", p.getDescription());
                    assertEquals(1200.0, p.getPrice());
                    assertEquals("Electronics", p.getCategory());
                    assertEquals(3, p.getCategoryId());
                    assertTrue(p.getOrderProducts().isEmpty());
                });
    }
}