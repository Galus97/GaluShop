package pl.galushop.GaluShop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE warehouse_product SET is_deleted = true WHERE warehouse_product_id = ?")
@SQLRestriction("is_deleted = false")
@Table(name = "warehouse_product")
public class WarehouseProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_product_id")
    private Long warehouseProductId;

    @OneToOne
    private Product product;

    @Min(0)
    private Integer quantity;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
