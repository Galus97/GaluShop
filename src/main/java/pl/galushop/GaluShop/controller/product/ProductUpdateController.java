package pl.galushop.GaluShop.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.ProductRequest;
import pl.galushop.GaluShop.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductUpdateController {
    private final ProductService productService;

    @PutMapping("/update")
    public String updateProduct(@RequestBody ProductRequest productRequest){

    }
}
