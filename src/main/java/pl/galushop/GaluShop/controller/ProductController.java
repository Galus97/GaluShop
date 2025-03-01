package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.ProductRequest;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.service.ProductFacadeService;
import pl.galushop.GaluShop.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final ProductFacadeService productFacadeService;

    @GetMapping("/{id}")
    public ResponseEntity<Product> showProduct(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody ProductRequest productRequest) {
        productFacadeService.saveProductWithImages(productRequest);
        return ResponseEntity.ok(productService.getProduct(productRequest.getProductId()));
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody ProductRequest productRequest){
        productService.updateProduct(productRequest);
        return ResponseEntity.ok(productService.getProduct(productRequest.getProductId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
