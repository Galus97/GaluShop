package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.PaymentsRequest;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.Payments;
import pl.galushop.GaluShop.repository.PaymentsRepository;

@Service
@RequiredArgsConstructor
public class PaymentsService {
    private final PaymentsRepository paymentsRepository;
    private final MessageService messageService;
    private final OrderService orderService;
    public void savePayments(PaymentsRequest paymentsRequest){
        if(paymentsRequest == null){
            throw new IllegalArgumentException(messageService.getMessage("error.paymentsRequestIsNull"));
        }
        Order order = orderService.getOrder(paymentsRequest.getOrderId());
        Payments payments = new Payments();
        payments.setTotalAmount(payments.getTotalAmount());
        payments.setPaymentStatus(paymentsRequest.getPaymentStatus());
        payments.setOrder(order);
        paymentsRepository.save(payments);
    }
}
