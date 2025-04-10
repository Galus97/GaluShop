package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.response.ProductImagesResponse;
import pl.galushop.GaluShop.service.ProductFacadeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class AllProductImagesController {
    private final ProductFacadeService productFacadeService;

    @GetMapping("/product/{id}")
    public ResponseEntity<List<ProductImagesResponse>> getAllImagesFromProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productFacadeService.getAllImagesByProductId(id));
    }
}
