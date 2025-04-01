package pl.galushop.GaluShop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.galushop.GaluShop.configuration.SpringSecurity;
import pl.galushop.GaluShop.dto.request.ProductImageRequest;
import pl.galushop.GaluShop.dto.request.ProductRequest;
import pl.galushop.GaluShop.dto.response.ProductResponse;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.exception.ProductNotFoundException;
import pl.galushop.GaluShop.service.ProductFacadeService;
import pl.galushop.GaluShop.service.ProductService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@Import(SpringSecurity.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService service;
    @MockBean
    private ProductFacadeService productFacadeService;
    private ProductRequest request;
    private ProductResponse response;

    @BeforeEach
    void setUp() {
        ProductImageRequest productRequest = new ProductImageRequest();
        productRequest.setImagesId(1L);
        productRequest.setProduct(new Product());
        productRequest.setImgSrc("Img Src");
        productRequest.setAltImg("Alt Img");
        request = ProductRequest.builder()
                .productId(null)
                .productName("Product name")
                .description("Description of the product")
                .price(10.0)
                .category("Electronic")
                .categoryId(1)
                .productImages(Arrays.asList(productRequest))
                .build();
        response = new ProductResponse(1L, "Product name", "Description of the product",
                10.0, "Electronic", 1, new ArrayList<>());
    }

    @Test
    void givenExistingId_whenShowProduct_thenReturnsProduct() throws Exception {
        //given
        when(service.getProductResponse(anyLong())).thenReturn(response);
        //then
        mockMvc.perform(get("/product/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(1L))
                .andExpect(jsonPath("$.productName").value("Product name"))
                .andExpect(jsonPath("$.description").value("Description of the product"))
                .andExpect(jsonPath("$.price").value(10))
                .andExpect(jsonPath("$.category").value("Electronic"))
                .andExpect(jsonPath("$.categoryId").value(1))
                .andExpect(jsonPath("$.products", hasSize(0)));
        verify(service, times(1)).getProductResponse(1L);
    }

    @Test
    void givenNonExistentId_whenShowProduct_thenReturnsNotFound() throws Exception {
        //given
        when(service.getProductResponse(anyLong())).thenThrow(ProductNotFoundException.class);
        //then
        mockMvc.perform(get("/product/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(service, times(1)).getProductResponse(999L);
    }

    @Test
    void givenNonExistentId_whenShowProduct_thenReturnsBadRequest() throws Exception {
        //given
        when(service.getProductResponse(anyLong())).thenThrow(IllegalArgumentException.class);
        //then
        mockMvc.perform(get("/product/-1"))
                .andExpect(status().isBadRequest());
        verify(service, times(1)).getProductResponse(-1L);
    }

    @Test
    void givenCorrectRequest_whenSaveProduct_thenReturnsProduct() throws Exception {
        //given
        when(productFacadeService.saveProductWithImages(any(ProductRequest.class))).thenReturn(response);
        //then
        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value(1L))
                .andExpect(jsonPath("$.productName").value("Product name"));
        verify(productFacadeService, times(1)).saveProductWithImages(request);
    }

    @Test
    void givenCorrectRequest_whenUpdateProduct_thenReturnsProduct() throws Exception {
        //given
        when(service.updateProduct(any(ProductRequest.class))).thenReturn(response);
        //then
        mockMvc.perform(put("/product")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(1));
        verify(service, times(1)).updateProduct(request);

    }

    @Test
    void givenExistingId_whenDeleteProduct_thenDeletesProduct() throws Exception {
        //given
        doNothing().when(service).deleteProduct(anyLong());
        //then
        mockMvc.perform(delete("/product/1"))
                .andExpect(status().isNoContent());
        verify(service, times(1)).deleteProduct(1L);
    }

    @Test
    void givenNonExistentId_whenDeleteProduct_thenDeletesProduct() throws Exception {
        //given
        doThrow(IllegalArgumentException.class).when(service).deleteProduct(anyLong());
        //then
        mockMvc.perform(delete("/product/-1"))
                .andExpect(status().isBadRequest());
        verify(service, times(1)).deleteProduct(-1L);
    }
}