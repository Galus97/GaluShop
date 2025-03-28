package pl.galushop.GaluShop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import pl.galushop.GaluShop.configuration.SpringSecurity;
import pl.galushop.GaluShop.dto.response.ProductImagesResponse;
import pl.galushop.GaluShop.service.ProductFacadeService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AllProductImagesController.class)
@Import(SpringSecurity.class)
@AutoConfigureMockMvc(addFilters = false)
class AllProductImagesControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    ProductFacadeService productFacadeService;

    @Test
    void givenExisting_whenGetAllImagesFromProduct_thenReturnsResponseEntityWithProductImagesResponseList() throws Exception {
        //given
        List<ProductImagesResponse> responseList = Arrays.asList(
                new ProductImagesResponse(1L, "Img Src","Alt Img", 1L));
        when(productFacadeService.getAllImagesByProductId(anyLong())).thenReturn(responseList);
        //then
        mockMvc.perform(get("/images/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].imagesId", is(1)))
                .andExpect(jsonPath("$[0].imgSrc", is("Img Src")))
                .andExpect(jsonPath("$[0].altImg", is("Alt Img")))
                .andExpect(jsonPath("$[0].productId", is(1)));
            verify(productFacadeService, times(1)).getAllImagesByProductId(eq(1L));
    }

    @Test
    void givenNonExistingProductId_whenGetAllImagesFromProduct_thenReturnsEmptyList() throws Exception {
        //given
        when(productFacadeService.getAllImagesByProductId(anyLong())).thenReturn(Collections.emptyList());
        //then
        mockMvc.perform(get("/images/product/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
        verify(productFacadeService, times(1)).getAllImagesByProductId(eq(999L));
    }
}