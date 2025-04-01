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
import pl.galushop.GaluShop.dto.response.ProductImagesResponse;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.exception.ProductImagesNotFoundException;
import pl.galushop.GaluShop.service.ProductImagesService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Test
    void givenExistingId_whenShowProductImages_thenReturnsProductImages() throws Exception{
        //given
        when(service.getProductImages(anyLong())).thenReturn(response);
        //then
        mockMvc.perform(get("/images/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.imagesId").value(1L))
                .andExpect(jsonPath("$.imgSrc").value("Image src"))
                .andExpect(jsonPath("$.altImg").value("Image alt"))
                .andExpect(jsonPath("$.productId").value(1L));
        verify(service, times(1)).getProductImages(1L);
    }

    @Test
    void givenNonExistentId_whenShowProductImages_thenReturnsNotFound() throws Exception{
        //given
        when(service.getProductImages(anyLong())).thenThrow(ProductImagesNotFoundException.class);
        //then
        mockMvc.perform(get("/images/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(service, times(1)).getProductImages(999L);
    }

    @Test
    void givenInvalidId_whenShowProductImages_thenReturnsBadRequest() throws Exception{
        //given
        when(service.getProductImages(anyLong())).thenThrow(IllegalArgumentException.class);
        //then
        mockMvc.perform(get("/images/-1"))
                .andExpect(status().isBadRequest());
        verify(service, times(1)).getProductImages(-1L);
    }

    @Test
    void givenCorrectRequest_whenSaveProductImages_thenReturnsProductImages() throws Exception{
        //given
        when(service.saveProductImages(any(ProductImageRequest.class))).thenReturn(response);
        //then
        mockMvc.perform(post("/images")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.imagesId").value(1L))
                .andExpect(jsonPath("$.imgSrc").value("Image src"));
        verify(service, times(1)).saveProductImages(request);
    }

    @Test
    void givenCorrectRequest_updateSaveProductImages_thenReturnsProductImages() throws Exception{
        //given
        when(service.updateProductImages(any(ProductImageRequest.class))).thenReturn(response);
        //then
        mockMvc.perform(put("/images")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.imagesId").value(1L))
                .andExpect(jsonPath("$.imgSrc").value("Image src"));
        verify(service, times(1)).updateProductImages(request);
    }
}