package pl.galushop.GaluShop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "warehouseProduct")
public class WarehouseProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouseProductId;

    @OneToOne
    private Product product;

    @Min(0)
    private Integer quantity;
}
