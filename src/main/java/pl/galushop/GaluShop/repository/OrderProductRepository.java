package pl.galushop.GaluShop.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.galushop.GaluShop.model.OrderProduct;
import pl.galushop.GaluShop.model.OrderProductId;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
    List<OrderProduct> findByOrder_OrderId(Long orderId);

    List<OrderProduct> findByProduct_ProductId(Long productId);
}
