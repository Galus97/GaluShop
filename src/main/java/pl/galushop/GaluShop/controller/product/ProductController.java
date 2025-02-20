package pl.galushop.GaluShop.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.ProductRequest;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<Product> showProduct(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody ProductRequest productRequest) {
        productService.saveProduct(productRequest);
        return ResponseEntity.ok(productService.getProductById(productRequest.getProductId()));
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody ProductRequest productRequest){
        productService.updateProduct(productRequest);
        return ResponseEntity.ok(productService.getProductById(productRequest.getProductId()));
    }
}
