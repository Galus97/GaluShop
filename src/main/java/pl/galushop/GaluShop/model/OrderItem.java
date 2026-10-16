package pl.galushop.GaluShop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE orders_product SET is_deleted = true WHERE order_product_id = ?")
@SQLRestriction("is_deleted = false")
@Table(name = "orders_item")
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

    public OrderItem(Order order, Product product, Integer productQuantity) {
        this.order = order;
        this.product = product;
        this.productQuantity = productQuantity;
        this.orderItemId = new OrderItemId(order.getOrderId(), product.getProductId());
    }
}
