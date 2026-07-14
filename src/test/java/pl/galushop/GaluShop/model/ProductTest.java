package pl.galushop.GaluShop.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    private Product product;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

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

    @Test
    void shouldValidateProductNameSize() {
        Product invalidProduct = Product.builder()
                .productName("AB") // Too short (min = 3)
                .description("Valid description with enough length.")
                .price(10.0)
                .category("Test Category")
                .categoryId(1)
                .build();

        Set<ConstraintViolation<Product>> violations = validator.validate(invalidProduct);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .contains("size must be between 3 and 2147483647");
    }

    @Test
    void shouldValidateDescriptionSize() {
            Product invalidProduct = Product.builder()
                    .productName("Valid Name")
                    .description("Short desc") // Too short
                    .price(10.0)
                    .category("Test Category")
                    .categoryId(1)
                    .build();

        Set<ConstraintViolation<Product>> violations = validator.validate(invalidProduct);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .contains("size must be between 20 and 2147483647");
    }

    @Test
    void shouldValidatePriceMinimumValue() {
            Product invalidProduct = Product.builder()
                    .productName("Valid Name")
                    .description("Valid description with enough length.")
                    .price(0.0) // Below minimum
                    .category("Test Category")
                    .categoryId(1)
                    .build();

        Set<ConstraintViolation<Product>> violations = validator.validate(invalidProduct);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .contains("must be greater than or equal to 1");
    }

    @Test
    void shouldValidateCategoryNotBlank() {
            Product invalidProduct = Product.builder()
                    .productName("Valid Name")
                    .description("Valid description with enough length.")
                    .price(10.0)
                    .category(" ") // Blank
                    .categoryId(1)
                    .build();
        Set<ConstraintViolation<Product>> violations = validator.validate(invalidProduct);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .contains("must not be blank");
    }
}