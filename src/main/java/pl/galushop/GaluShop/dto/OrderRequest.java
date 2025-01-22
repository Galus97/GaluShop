package pl.galushop.GaluShop.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderRequest {
    private Long userId;
    private LocalDateTime localDateTime;
    private List<Long> productIds;
}
