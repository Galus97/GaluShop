package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.request.ProductImageRequest;
import pl.galushop.GaluShop.dto.request.ProductRequest;
import pl.galushop.GaluShop.dto.response.ProductResponse;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.OrderProduct;
import pl.galushop.GaluShop.entity.OrderProductId;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.exception.ProductNotFoundException;
import pl.galushop.GaluShop.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    ProductRepository repository;
    @Mock
    MessageService messageService;
    @InjectMocks
    ProductService service;
    private Product product;
    private ProductRequest productRequest;

    @BeforeEach
    void setUp(){
        OrderProduct orderProduct = OrderProduct.builder()
                .id(new OrderProductId())
                .order(new Order())
                .product(new Product())
                .quantity(10)
                .build();
        List<OrderProduct> orderProductList = Arrays.asList(orderProduct);
        product = Product.builder()
                .productId(1L)
                .productName("Product name")
                .description("Description of the product")
                .price(10.0)
                .category("Electronic")
                .categoryId(1)
                .orderProducts(orderProductList)
                .build();
        productRequest = ProductRequest.builder()
                .productId(null)
                .productName("Product name")
                .description("Description of the product")
                .price(10.0)
                .category("Electronic")
                .categoryId(1)
                .productImages(new ArrayList<ProductImageRequest>())
                .build();
    }

    @Test
    void givenExistingProductId_whenGetProductEntity_thenReturnsProductEntity(){
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.of(product));
        //when
        Product product = service.getProductEntity(1L);
        //then
        assertNotNull(product);
        assertEquals("Product name", product.getProductName());
        assertEquals("Description of the product", product.getDescription());
        assertEquals(10d, product.getPrice());
        assertEquals("Electronic", product.getCategory());
        assertEquals(1, product.getCategoryId());
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void givenNonExistentProductId_whenGetProductEntity_thenThrowsException(){
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(ProductNotFoundException.class, () -> service.getProductEntity(1L));
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void givenInvalidProductId_whenGetProductEntity_thenThrowsException(){
        //then
        assertThrows(IllegalArgumentException.class, () -> service.getProductEntity(-1L));
        verify(repository, times(0)).findById(anyLong());
    }
    @Test
    void givenNullProductId_whenGetProductEntity_thenThrowsException(){
        //then
        assertThrows(IllegalArgumentException.class, () -> service.getProductEntity(null));
        verify(repository, times(0)).findById(anyLong());
    }

    @Test
    void givenExistingProductId_whenGetProductResponse_thenReturnsProductResponse(){
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.of(product));
        //when
        ProductResponse response = service.getProductResponse(1L);
        //then
        assertNotNull(response);
        assertEquals("Product name", response.productName());
        assertEquals("Description of the product", response.description());
        assertEquals(10d, response.price());
        assertEquals("Electronic", response.category());
        assertEquals(1, response.categoryId());
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void givenCorrectRequest_whenSaveProductEntity_thenReturnsProductEntity(){
        //given
        when(repository.save(any(Product.class))).thenReturn(product);
        //when
        Product productEntity = service.saveProductEntity(productRequest);
        //then
        assertNotNull(productEntity);
        assertEquals(product.getProductId(), productEntity.getProductId());
        assertEquals(product.getDescription(), productEntity.getDescription());
        assertEquals(product.getPrice(), productEntity.getPrice());
        assertEquals(product.getCategory(), productEntity.getCategory());
        verify(repository, times(1)).save(any(Product.class));
    }

    @Test
    void givenNullRequest_whenSaveProductEntity_thenThrowsException(){
        //then
        assertThrows(IllegalArgumentException.class, () -> service.saveProductEntity(null));
        verify(repository, times(0)).save(any(Product.class));
    }

    @Test
    void givenCorrectRequest_whenSaveProductResponse_thenReturnsProductResponse(){
        //given
        when(repository.save(any(Product.class))).thenReturn(product);
        //when
        ProductResponse response = service.saveProductResponse(productRequest);
        //then
        assertNotNull(response);
        assertEquals(product.getProductId(), response.productId());
        assertEquals(product.getDescription(), response.description());
        assertEquals(product.getPrice(), response.price());
        assertEquals(product.getCategory(), response.category());
        verify(repository, times(1)).save(any(Product.class));
    }

    @Test
    void givenExistingProductId_whenDeleteProduct_thenDeletesProduct(){
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.of(product));
        //when
        service.deleteProduct(1L);
        //then
        verify(repository, times(1)).delete(product);
    }

    @Test
    void givenNonExistentProductId_whenDeleteProduct_thenThrowsException(){
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(ProductNotFoundException.class, () -> service.deleteProduct(1L));
        verify(repository, times(0)).delete(product);
    }

    @Test
    void givenCorrectRequest_whenUpdateProduct_thenReturnsProductResponse(){
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.of(product));
        when(repository.save(any(Product.class))).thenReturn(product);
        productRequest.setProductId(1L);
        //when
        ProductResponse response = service.updateProduct(productRequest);
        //then
        assertEquals("Product name", response.productName());
        assertEquals("Description of the product", response.description());
        assertEquals(10d, response.price());
        assertEquals("Electronic", response.category());
        assertEquals(1, response.categoryId());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Product.class));
    }

    @Test
    void givenNotEmptyListWithIds_whenGetAllProductByIds_thenReturnsProductList(){
        //given
        List<Long> productIds = Arrays.asList(product.getProductId());
        List<Product> productList = Arrays.asList(product);
        when(repository.findAllById(productIds)).thenReturn(productList);
        //when
        List<Product> allProductByIds = service.getAllProductByIds(productIds);
        //then
        assertEquals(1, allProductByIds.size());
        assertEquals(1L, allProductByIds.get(0).getProductId());
        assertEquals("Product name", allProductByIds.get(0).getProductName());
        verify(repository, times(1)).findAllById(productIds);
    }
}