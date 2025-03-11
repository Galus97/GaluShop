package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.PaymentsRequest;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.Payments;
import pl.galushop.GaluShop.exception.PaymentNotFoundException;
import pl.galushop.GaluShop.repository.PaymentsRepository;

import java.util.List;

/**
 * Service class responsible for managing payment operations.
 */
@Service
@RequiredArgsConstructor
public class PaymentsService {
    private final PaymentsRepository paymentsRepository;
    private final MessageService messageService;
    private final OrderService orderService;
    private final UserService userService;

    /**
     * Retrieves a payment by its ID.
     *
     * @param paymentId The ID of the payment to retrieve.
     * @return The retrieved payment entity.
     * @throws IllegalArgumentException if the payment ID is null or invalid.
     * @throws PaymentNotFoundException if no payment is found with the given ID.
     */
    public Payments getPaymentById(Long paymentId){
        if(paymentId == null || paymentId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidPaymentId", paymentId));
        }
        return paymentsRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(messageService.getMessage("error.paymentsNotFound", paymentId)));
    }

    /**
     * Retrieves a payment by the associated order ID.
     *
     * @param orderId The ID of the order.
     * @return The payment associated with the given order.
     * @throws IllegalArgumentException if the order ID is null or invalid.
     * @throws PaymentNotFoundException if no payment is found for the given order ID.
     */
    public Payments getPaymentByOrderId(Long orderId){
        if(orderId == null || orderId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidOrderId", orderId));
        }
        return paymentsRepository.findByOrder_OrderId(orderId)
                .orElseThrow(() -> new PaymentNotFoundException(messageService.getMessage("error.paymentsNotFoundByOrderId", orderId)));
    }

    /**
     * Retrieves all payments made by a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of payments associated with the user.
     * @throws IllegalArgumentException if the user ID is null or invalid.
     */
    public List<Payments> getAllPaymentsByUserId(Long userId){
        if(userId == null || userId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidUserId", userId));
        }
        userService.getUser(userId);

        return paymentsRepository.findAllByUser_UserId(userId);
    }

    /**
     * Saves a new payment associated with an order.
     *
     * @param paymentsRequest The request object containing payment details.
     * @return The created Payment
     * @throws IllegalArgumentException if the request object is null.
     * @throws pl.galushop.GaluShop.exception.OrderNotFoundException if no order is found with the given ID
     */
    @Transactional
    public Payments savePayment(PaymentsRequest paymentsRequest){
        Payments payments = buildPayment(paymentsRequest);
        return paymentsRepository.save(payments);
    }


    /**
     * Deletes a payment by its ID.
     *
     * @param paymentId The ID of the payment to delete.
     * @throws IllegalArgumentException if the payment ID is null or invalid.
     * @throws PaymentNotFoundException if no payment is found with the given ID.
     */
    public void deletePayment(Long paymentId){
        if(paymentId == null || paymentId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidPaymentId", paymentId));
        }
        Payments payments = paymentsRepository.findById(paymentId).
                orElseThrow(() -> new PaymentNotFoundException(messageService.getMessage("error.paymentsNotFound", paymentId)));
        paymentsRepository.delete(payments);
    }

    /**
     * Updates an existing payment's details.
     *
     * @param paymentsRequest The request object containing updated payment details.
     * @throws IllegalArgumentException if the request object is null or contains an invalid ID.
     * @throws PaymentNotFoundException if no payment is found with the given ID.
     */
    @Transactional
    public void updatePayment(PaymentsRequest paymentsRequest){
        if(paymentsRequest == null){
            throw new IllegalArgumentException(messageService.getMessage("paymentsRequestIsNull"));
        } else if (paymentsRequest.getPaymentsId() < 0) {
            throw new IllegalArgumentException(messageService.getMessage("error.invalidPaymentId", paymentsRequest.getPaymentsId()));
        }
        Order order = orderService.getOrder(paymentsRequest.getOrderId());

        Payments existingPayment = paymentsRepository.findById(paymentsRequest.getPaymentsId())
                .orElseThrow(() -> new PaymentNotFoundException(messageService.getMessage("error.paymentsNotFound", paymentsRequest.getPaymentsId())));
        existingPayment.setPaymentStatus(paymentsRequest.getPaymentStatus());
        existingPayment.setTotalAmount(paymentsRequest.getTotalAmount());
        existingPayment.setOrder(order);

        paymentsRepository.save(existingPayment);
    }

    /**
     * Builds a Payment entity from the given request
     *
     * @param paymentsRequest The request object containing payment details.
     * @throws IllegalArgumentException if the request object is null.
     * @throws pl.galushop.GaluShop.exception.OrderNotFoundException if no order is found with the given ID
     */
    private Payments buildPayment(PaymentsRequest paymentsRequest) {
        if(paymentsRequest == null){
            throw new IllegalArgumentException(messageService.getMessage("error.paymentsRequestIsNull"));
        }
        Order order = orderService.getOrder(paymentsRequest.getOrderId());

        return Payments.builder()
                .totalAmount(paymentsRequest.getTotalAmount())
                .paymentStatus(paymentsRequest.getPaymentStatus())
                .order(order)
                .build();
    }
}
