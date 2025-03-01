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
import pl.galushop.GaluShop.dto.ProductImageRequest;
import pl.galushop.GaluShop.entity.ProductImages;
import pl.galushop.GaluShop.service.ProductImagesService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ProductImagesController {
    private final ProductImagesService productImagesService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductImages> showProductImages(@PathVariable Long id) {
        return ResponseEntity.ok(productImagesService.getProductImages(id));
    }

    @PostMapping
    public ResponseEntity<ProductImages> saveProductImages(@RequestBody ProductImageRequest productImageRequest) {
        ProductImages savedProductImages = productImagesService.saveProductImages(productImageRequest);
        return ResponseEntity.created(URI.create("/images/" + savedProductImages.getImagesId()))
                .body(savedProductImages);
    }

    @PutMapping
    public ResponseEntity<ProductImages> updateProductImages(@RequestBody ProductImageRequest productImageRequest) {
        productImagesService.updateProductImages(productImageRequest);
        return ResponseEntity.ok(productImagesService.getProductImages(productImageRequest.getImagesId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductImages(@PathVariable Long id) {
        productImagesService.deleteProductImages(id);
        return ResponseEntity.noContent().build();
    }
}
