package pl.galushop.GaluShop.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.galushop.GaluShop.model.OrderItem;
import pl.galushop.GaluShop.model.OrderItemId;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderItem, OrderItemId> {
    List<OrderItem> findByOrder_OrderId(Long orderId);

    List<OrderItem> findByProduct_ProductId(Long productId);
}
