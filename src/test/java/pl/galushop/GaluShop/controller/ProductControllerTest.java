package pl.galushop.GaluShop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import pl.galushop.GaluShop.configuration.SpringSecurity;
import pl.galushop.GaluShop.dto.request.ProductRequest;
import pl.galushop.GaluShop.dto.response.ProductResponse;
import pl.galushop.GaluShop.service.ProductFacadeService;
import pl.galushop.GaluShop.service.ProductService;

import java.util.ArrayList;

@WebMvcTest(ProductController.class)
@Import(SpringSecurity.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    @MockBean
    private ProductFacadeService productFacadeService;
    private ProductRequest request;
    private ProductResponse response;

    @BeforeEach
    void setUp() {
        request = ProductRequest.builder()
                .productId(null)
                .productName("Product name")
                .description("Description of the product")
                .price(10.0)
                .category("Electronic")
                .categoryId(1)
                .productImages(new ArrayList<>())
                .build();
        response = new ProductResponse(1L, "Product name", "Description of the product",
                10.0, "Electronic", 1, new ArrayList<>());
    }
}