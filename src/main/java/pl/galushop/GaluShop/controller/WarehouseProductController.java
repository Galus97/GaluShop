package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.entity.WarehouseProduct;
import pl.galushop.GaluShop.service.ProductService;
import pl.galushop.GaluShop.service.WarehouseProductService;

@RestController
@RequiredArgsConstructor
public class WarehouseProductController {

    private final WarehouseProductService warehouseProductService;
    private final ProductService productService;

    @GetMapping("/warehouse/addProduct")
    public String addProductToWarehouse(){

        Product product = new Product();
        product.setProductName("Product 1");
        product.setPrice(20.50);
        product.setDescription("Description of first Product");
        product.setCategory("Category 1");
        product.setCategoryId(1);
        productService.saveProductToDatabase(product);

        WarehouseProduct warehouseProduct = new WarehouseProduct();
        warehouseProduct.setProduct(product);
        warehouseProduct.setQuantity(10);
        warehouseProductService.addProductToWarehouse(warehouseProduct);

        return "success";
    }
}
