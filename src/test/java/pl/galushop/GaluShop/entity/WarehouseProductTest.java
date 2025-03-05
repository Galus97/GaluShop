package pl.galushop.GaluShop.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class WarehouseProductTest {
    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
    }

    @Test
    void shouldCreateWarehouseProductWithValidData() {
        WarehouseProduct warehouseProduct = WarehouseProduct.builder()
                .warehouseProductId(1L)
                .product(product)
                .quantity(10)
                .build();

        assertThat(warehouseProduct).isNotNull();
        assertThat(warehouseProduct.getWarehouseProductId()).isEqualTo(1L);
        assertThat(warehouseProduct.getProduct()).isEqualTo(product);
        assertThat(warehouseProduct.getQuantity()).isEqualTo(10);
    }
}