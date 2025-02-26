package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.PaymentsRequest;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.Payments;
import pl.galushop.GaluShop.exception.PaymentNotFoundException;
import pl.galushop.GaluShop.repository.PaymentsRepository;

@Service
@RequiredArgsConstructor
public class PaymentsService {
    private final PaymentsRepository paymentsRepository;
    private final MessageService messageService;
    private final OrderService orderService;

    public Payments getPaymentById(Long paymentId){
        if(paymentId == null || paymentId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidPaymentId", paymentId));
        }
        return paymentsRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(messageService.getMessage("error.paymentsNotFound", paymentId)));
    }

    public Payments getPaymentByOrderId(Long orderId){
        if(orderId == null || orderId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidOrderId", orderId));
        }
        return paymentsRepository.findByOrder_OrderId(orderId)
                .orElseThrow(() -> new PaymentNotFoundException(messageService.getMessage("error.paymentsNotFoundByOrderId", orderId)));
    }

    public void savePayment(PaymentsRequest paymentsRequest){
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

    public void deletePayment(Long paymentId){
        if(paymentId == null || paymentId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidPaymentId", paymentId));
        }
        Payments payments = paymentsRepository.findById(paymentId).
                orElseThrow(() -> new PaymentNotFoundException(messageService.getMessage("error.paymentsNotFound", paymentId)));
        paymentsRepository.delete(payments);
    }
}
