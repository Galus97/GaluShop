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
import pl.galushop.GaluShop.dto.request.ProductImageRequest;
import pl.galushop.GaluShop.dto.request.ProductRequest;
import pl.galushop.GaluShop.dto.response.ProductImagesResponse;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.service.ProductImagesService;

@WebMvcTest(ProductImagesController.class)
@Import(SpringSecurity.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductImagesControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductImagesService service;
    private ProductImagesResponse response;
    private ProductImageRequest request;

    @BeforeEach
    void setUp(){
        Product product = new Product();
        product.setProductId(1L);

        request = ProductImageRequest.builder()
                .imagesId(1L)
                .imgSrc("Image src")
                .altImg("Image alt")
                .product(product)
                .build();
        response = new ProductImagesResponse(1L, "Image src", "Image alt", product.getProductId());
    }


}