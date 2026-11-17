package pl.galushop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.galushop.model.Order;
import pl.galushop.model.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderIdAndUser(Long orderId, User user);
}
