package pl.galushop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE order_items SET is_deleted = true WHERE order_item_id = ?")
@SQLRestriction("is_deleted = false")
@Table(name = "order_items")
public class OrderItem {
    @EmbeddedId
    @Column(name = "order_item_id")
    private OrderItemId orderItemId = new OrderItemId();

    @ManyToOne
    @MapsId("order_Id")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("product_Id")
    @JoinColumn(name = "product_id")
    private Product product;

    @Min(1)
    private Integer productQuantity;

    @NotNull
    private Double totalCostOfProduct;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
