package pl.galushop.GaluShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.component.OrderStatus;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<List<Order>> findAllByUser_UserId(Long userId);

    Optional<Order> findByUser_UserId(Long userId);
}
