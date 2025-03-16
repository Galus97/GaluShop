package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.component.ErrorMessages;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.PaymentRequest;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.Payment;
import pl.galushop.GaluShop.exception.PaymentNotFoundException;
import pl.galushop.GaluShop.repository.PaymentRepository;

import java.util.List;

/**
 * Service class responsible for managing payment operations.
 */
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
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
    public Payment getPaymentById(Long paymentId){
        throwIfIdIsInvalid(paymentId, ErrorMessages.INVALID_PAYMENT_ID);

        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(messageService.getMessage(ErrorMessages.PAYMENT_NOT_FOUND, paymentId)));
    }

    /**
     * Retrieves a payment by the associated order ID.
     *
     * @param orderId The ID of the order.
     * @return The payment associated with the given order.
     * @throws IllegalArgumentException if the order ID is null or invalid.
     * @throws PaymentNotFoundException if no payment is found for the given order ID.
     */
    public Payment getPaymentByOrderId(Long orderId){
        throwIfIdIsInvalid(orderId, ErrorMessages.INVALID_ORDER_ID);

        return paymentRepository.findByOrder_OrderId(orderId)
                .orElseThrow(() -> new PaymentNotFoundException(messageService.getMessage("error.paymentsNotFoundByOrderId", orderId)));
    }

    /**
     * Retrieves all payments made by a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of payments associated with the user.
     * @throws IllegalArgumentException if the user ID is null or invalid.
     */
    public List<Payment> getAllPaymentsByUserId(Long userId){
        throwIfIdIsInvalid(userId, ErrorMessages.INVALID_USER_ID);

        //Throw if user not found
        userService.getUser(userId);

        return paymentRepository.findAllByUser_UserId(userId);
    }

    /**
     * Saves a new payment associated with an order.
     *
     * @param paymentRequest The request object containing payment details.
     * @return The created Payment
     * @throws IllegalArgumentException if the request object is null.
     * @throws pl.galushop.GaluShop.exception.OrderNotFoundException if no order is found with the given ID
     */
    @Transactional
    public Payment savePayment(PaymentRequest paymentRequest){
        return paymentRepository.save(buildPayment(paymentRequest));
    }


    /**
     * Deletes a payment by its ID.
     *
     * @param paymentId The ID of the payment to delete.
     * @throws IllegalArgumentException if the payment ID is null or invalid.
     * @throws PaymentNotFoundException if no payment is found with the given ID.
     */
    public void deletePayment(Long paymentId){
        throwIfIdIsInvalid(paymentId, ErrorMessages.INVALID_PAYMENT_ID);

        Payment payment = paymentRepository.findById(paymentId).
                orElseThrow(() -> new PaymentNotFoundException(messageService.getMessage(ErrorMessages.PAYMENT_NOT_FOUND, paymentId)));
        paymentRepository.delete(payment);
    }

    /**
     * Updates an existing payment's details.
     *
     * @param paymentRequest The request object containing updated payment details.
     * @throws IllegalArgumentException if the request object is null or contains an invalid ID.
     * @throws PaymentNotFoundException if no payment is found with the given ID.
     */
    @Transactional
    public void updatePayment(PaymentRequest paymentRequest){
        throwIfIdIsInvalid(paymentRequest.getPaymentId(), ErrorMessages.INVALID_PAYMENT_ID);

        Order order = orderService.getOrderEntity(paymentRequest.getOrderId());

        Payment existingPayment = paymentRepository.findById(paymentRequest.getPaymentId())
                .orElseThrow(() -> new PaymentNotFoundException(messageService.getMessage(ErrorMessages.PAYMENT_NOT_FOUND, paymentRequest.getPaymentId())));
        existingPayment.setPaymentStatus(paymentRequest.getPaymentStatus());
        existingPayment.setTotalAmount(paymentRequest.getTotalAmount());
        existingPayment.setOrder(order);

        paymentRepository.save(existingPayment);
    }

    /**
     * Builds a Payment entity from the given request
     *
     * @param paymentRequest The request object containing payment details.
     * @throws IllegalArgumentException if the request object is null.
     * @throws pl.galushop.GaluShop.exception.OrderNotFoundException if no order is found with the given ID
     */
    private Payment buildPayment(PaymentRequest paymentRequest) {
        throwIfIdIsInvalid(paymentRequest.getPaymentId(), ErrorMessages.INVALID_PAYMENT_ID);

        Order order = orderService.getOrderEntity(paymentRequest.getOrderId());

        return Payment.builder()
                .totalAmount(paymentRequest.getTotalAmount())
                .paymentStatus(paymentRequest.getPaymentStatus())
                .order(order)
                .build();
    }

    private void throwIfIdIsInvalid(Long id, String message){
        if(id == null || id <= 0){
            throw new IllegalArgumentException(messageService.getMessage(message, id));
        }
    }
}
