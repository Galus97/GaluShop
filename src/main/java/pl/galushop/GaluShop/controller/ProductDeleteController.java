package pl.galushop.GaluShop.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.ProductRequest;
import pl.galushop.GaluShop.service.ProductService;

@RestController
@RequiredArgsConstructor
public class ProductDeleteController {
    private final ProductService productService;

    @GetMapping("/deleteProduct")
    public String deleteProduct(@RequestBody ProductRequest productRequest){
        Long productId = productRequest.getProductId();
        if(productId > 0 && productId != null && productService.findProductById(productId) != null){
            productService.deleteProduct(productId);
            return "Success";
        }
        return "The product could not be removed";
    }
}
