package pl.galushop.GaluShop.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductImagesTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldCreateProductImagesSuccessfully() {
        ProductImages productImages = ProductImages.builder()
                .imgSrc("https://example.com/image.jpg")
                .altImg("Sample Image")
                .build();

        assertThat(productImages).isNotNull();
        assertThat(productImages.getImgSrc()).isEqualTo("https://example.com/image.jpg");
        assertThat(productImages.getAltImg()).isEqualTo("Sample Image");
    }

    @Test
    void shouldDetectBlankImgSrc() {
        // Given
        ProductImages productImages = ProductImages.builder()
                .imgSrc("") // Invalid blank field
                .altImg("Valid Alt Text")
                .build();

        // When
        Set<ConstraintViolation<ProductImages>> violations = validator.validate(productImages);

        // Then
        assertEquals(1, violations.size());
        assertThat(violations.iterator().next().getMessage()).isEqualTo("must not be blank");
    }
}