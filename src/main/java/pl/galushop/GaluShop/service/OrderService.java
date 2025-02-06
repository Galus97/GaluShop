package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.repository.OrderRepository;
import pl.galushop.GaluShop.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public void saveOrderToDatabase(Order order) {
        if (order != null) {
            orderRepository.save(order);
        }
    }

    public List<Order> getAllOrdersByUser(Long userId) {
        if (userId != null && userId > 0) {
            if (userRepository.findByUserId(userId).isPresent()) {
                return orderRepository.findAllByUser_UserId(userId);
            }
            throw new NoSuchElementException("User doesn't exist in database");
        }
        throw new IllegalArgumentException("User Id is invalid");
    }

    public Order showSpecificOrder(Long userId) {
        if (userId != null && userId > 0) {
            if (userRepository.findByUserId(userId).isPresent()) {
                return orderRepository.findByUser_UserId(userId).get();
            }
            throw new NoSuchElementException("User doesn't exist in database");
        }
        throw new IllegalArgumentException("User Id is invalid");
    }

    public void deleteOrder(Long orderId){
        if(orderId != null && orderId > 0){
            if (orderRepository.findById(orderId).isPresent()) {
                orderRepository.deleteById(orderId);
            }
            throw new NoSuchElementException("Order doesn't exist in database");
        }
        throw new IllegalArgumentException("Order Id is invalid");
    }
}
