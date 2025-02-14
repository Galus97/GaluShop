package pl.galushop.GaluShop.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.ProductRequest;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Product")
public class SaveProductController {

    private final ProductService productService;

    @PutMapping("/save")
    public ResponseEntity<Product> addNewProduct(@RequestBody ProductRequest productRequest) {
        productService.saveProduct(productRequest);
        return ResponseEntity.ok(productService.getProductById(productRequest.getProductId()));
    }
}
