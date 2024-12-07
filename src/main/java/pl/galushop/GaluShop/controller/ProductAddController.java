package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.entity.ProductImages;
import pl.galushop.GaluShop.service.ProductImagesService;
import pl.galushop.GaluShop.service.ProductService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductAddController {

    private final ProductService productService;
    private final ProductImagesService productImagesService;

    @GetMapping("/addProduct")
    public String getProductAdd() {
        Product product = new Product();
        product.setProductName("Product1");
        product.setCategory("Category1");
        product.setPrice(21.0);
        product.setCategoryId(1);
        product.setDescription("Description of added Product");
        productService.saveProductToDatabase(product);

        ProductImages productImages1 = new ProductImages();
        ProductImages productImages2 = new ProductImages();

        productImages1.setProduct(product);
        productImages1.setImgSrc("imgSrc");
        productImages1.setAltImg("altImg");

        productImages2.setProduct(product);
        productImages2.setImgSrc("imgSrc");
        productImages2.setAltImg("altImg");

        productImagesService.saveProductImagesToDatabase(productImages1);
        productImagesService.saveProductImagesToDatabase(productImages2);

        return "success";
    }
}
