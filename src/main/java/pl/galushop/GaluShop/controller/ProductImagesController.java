package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.service.ProductImagesService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ProductImagesController {
    private final ProductImagesService productImagesService;
}
