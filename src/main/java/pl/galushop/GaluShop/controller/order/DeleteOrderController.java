package pl.galushop.GaluShop.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.OrderRequest;
import pl.galushop.GaluShop.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class DeleteOrderController {
    private final OrderService orderService;

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteOrder(@RequestBody OrderRequest orderRequest){
        orderService.deleteOrder(orderRequest.getUserId());
        return ResponseEntity.noContent().build();
    }
}
