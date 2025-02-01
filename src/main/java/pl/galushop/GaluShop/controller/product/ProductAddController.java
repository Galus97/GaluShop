package pl.galushop.GaluShop.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private final ProductImagesService productImagesService;

    @GetMapping("/addProduct")
    public String addNewProduct(@RequestBody ProductRequest productRequest) {
        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setCategory(productRequest.getCategory());
        product.setPrice(productRequest.getPrice());
        product.setCategoryId(productRequest.getCategoryId());
        product.setDescription(productRequest.getDescription());
        productService.saveProductToDatabase(product);

        List<ProductImageRequest> productImagesList = productRequest.getProductImages();
        for (ProductImageRequest productImage : productImagesList) {
            ProductImages productImages = new ProductImages();
            productImages.setProduct(product);
            productImages.setImgSrc(productImage.getImgSrc());
            productImages.setAltImg(productImage.getAltImg());
            productImagesService.saveProductImagesToDatabase(productImages);
        }
        return "success";
    }
}
