package pl.galushop.GaluShop.dto.response;

import org.junit.jupiter.api.Test;
import pl.galushop.GaluShop.model.Product;
import pl.galushop.GaluShop.model.WarehouseProduct;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseProductResponseTest {

    @Test
    void givenWarehouseProduct_whenFromEntity_thenReturnsWarehouseProductResponse(){
        //given
        Product product = new Product();
        product.setProductId(1L);

        WarehouseProduct warehouseProduct = WarehouseProduct.builder()
                .warehouseProductId(1L)
                .product(product)
                .quantity(10)
                .build();
        //when
        WarehouseProductResponse response = WarehouseProductResponse.fromEntity(warehouseProduct);
        //then
        assertEquals(1L, response.warehouseProductId());
        assertEquals(1L, response.productId());
        assertEquals(10, response.quantity());
    }
}