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
@SQLDelete(sql = "UPDATE orders_product SET is_deleted = true WHERE order_product_id = ?")
@SQLRestriction("is_deleted = false")
@Table(name = "orders_product")
public class OrderProduct {

    @EmbeddedId
    @Column(name = "order_product_id")
    private OrderProductId orderProductId = new OrderProductId();

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @Min(0)
    private Integer quantity;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public OrderProduct(Order order, Product product, Integer quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.orderProductId = new OrderProductId(order.getOrderId(), product.getProductId());
    }
}
