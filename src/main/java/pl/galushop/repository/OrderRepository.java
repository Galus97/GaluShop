package pl.galushop.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.galushop.component.OrderStatus;
import pl.galushop.model.Order;
import pl.galushop.model.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByOrderIdAndUser(Long orderId, User user);

    List<Order> findAllByUser(User user, Pageable pageable);

    List<Order> findAllByOrderStatusAndUser(OrderStatus orderStatus, User user, Pageable pageable);
}
