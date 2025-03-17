package pl.galushop.GaluShop.dto.response;

import pl.galushop.GaluShop.dto.OrderProductDto;

import java.util.List;

public record ProductResponse (Long productId, String productName, String description, Double price,
                               String category, Integer categoryId, List<OrderProductDto> products){
}
