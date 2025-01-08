package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void saveOrderToDatabase(Order order) {
        if (order != null) {
            orderRepository.save(order);
        }
    }
}
