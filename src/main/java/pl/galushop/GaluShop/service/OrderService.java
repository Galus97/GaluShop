package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.repository.OrderRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    public void saveOrderToDatabase(Order order) {
        if (order != null) {
            orderRepository.save(order);
        }
    }

    public List<Order> getAllOrdersByUser(Long userId){
        if(userId != null && userId > 0 && userService.getUserById(userId).isPresent()){
                return orderRepository.findAllByUser_UserId(userId);

        }
        throw new IllegalArgumentException();
    }
}
