package pl.galushop.GaluShop.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.ProductImageRequest;
import pl.galushop.GaluShop.dto.ProductRequest;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.entity.ProductImages;
import pl.galushop.GaluShop.service.ProductImagesService;
import pl.galushop.GaluShop.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductAddController {

    private final ProductService productService;

    @GetMapping("/add")
    public String addNewProduct(@RequestBody ProductRequest productRequest) {
        productService.saveProductToDatabase(productRequest);
        return "success";
    }
}
