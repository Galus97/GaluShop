package pl.galushop.GaluShop.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    void shouldNotAllowNegativeQuantity() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        WarehouseProduct warehouseProduct = WarehouseProduct.builder()
                    .warehouseProductId(2L)
                    .product(product)
                    .quantity(-1) // Invalid value
                    .build();

        Set<ConstraintViolation<WarehouseProduct>> violations = validator.validate(warehouseProduct);

        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").contains("must be greater than or equal to 0");
    }
}