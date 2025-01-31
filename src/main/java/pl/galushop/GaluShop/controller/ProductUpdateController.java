package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.ProductRequest;
import pl.galushop.GaluShop.service.ProductService;

@RestController
@RequiredArgsConstructor
public class ProductUpdateController {
    private final ProductService productService;

    @GetMapping("/updateProduct")
    public String updateProduct(@RequestBody ProductRequest productRequest){
        Long productId = productRequest.getProductId();
        if(productId != null && productId > 0 && productService.findProductById(productId) != null){
            productService.updateProductByProductId(
                    productRequest.getProductId(),
                    productRequest.getProductName(),
                    productRequest.getDescription(),
                    productRequest.getPrice(),
                    productRequest.getCategory(),
                    productRequest.getCategoryId());
            return "Success";
        }
        return "The product could not be updated";
    }
}
