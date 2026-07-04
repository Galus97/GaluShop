package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.ServiceValidator;
import pl.galushop.GaluShop.component.ErrorMessages;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.request.PaymentRequest;
import pl.galushop.GaluShop.dto.response.PaymentResponse;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.Payment;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.exception.OrderNotFoundException;
import pl.galushop.GaluShop.exception.PaymentNotFoundException;
import pl.galushop.GaluShop.exception.UserNotFoundException;
import pl.galushop.GaluShop.repository.PaymentRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for managing payment operations such as creating,
 * retrieving, updating, and deleting payments. Coordinates with OrderService and UserService
 * to ensure valid associations.
 */
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final MessageService messageService;
    private final OrderService orderService;
    private final UserService userService;
    private final ServiceValidator serviceValidator;

    /**
     * Retrieves a payment by its ID.
     *
     * @param paymentId The ID of the payment to retrieve.
     * @return A response DTO representing the retrieved payment.
     * @throws IllegalArgumentException if the payment ID is null or invalid.
     * @throws PaymentNotFoundException if no payment is found with the given ID.
     */
    public PaymentResponse getPaymentResponse(Long paymentId) {
        serviceValidator.throwIfIdIsNotValid(paymentId, ErrorMessages.INVALID_PAYMENT_ID);
        return PaymentResponse.fromEntity(getPaymentOrThrow(paymentId, ErrorMessages.PAYMENT_NOT_FOUND));
    }

    /**
     * Retrieves a payment associated with a specific order ID.
     *
     * @param orderId The ID of the order.
     * @return A response DTO representing the payment associated with the given order.
     * @throws IllegalArgumentException if the order ID is null or invalid.
     * @throws PaymentNotFoundException if no payment is found for the given order ID.
     */
    public PaymentResponse getPaymentResponseByOrderId(Long orderId) {
        serviceValidator.throwIfIdIsNotValid(orderId, ErrorMessages.INVALID_ORDER_ID);
        return PaymentResponse.fromEntity(paymentRepository.findByOrder_OrderId(orderId)
                .orElseThrow(() -> new PaymentNotFoundException(
                        messageService.getMessage(ErrorMessages.PAYMENT_NOT_FOUND_BY_ORDER, orderId))));
    }

    /**
     * Retrieves all payments associated with a specific user ID.
     *
     * @param userId The ID of the user.
     * @return A list of response DTOs representing the user's payments.
     * @throws IllegalArgumentException if the user ID is null or invalid.
     */
    public List<PaymentResponse> getAllPaymentResponseByUserId(Long userId) {
        serviceValidator.throwIfIdIsNotValid(userId, ErrorMessages.INVALID_USER_ID);
        //Throws exception if user doesn't exist in database
        userService.getUserEntity(userId);

        return paymentRepository.findAllByUser_UserId(userId)
                .stream()
                .map(PaymentResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Saves a new payment associated with an order and a user.
     *
     * @param paymentRequest The request object containing payment details.
     * @return A response DTO representing the saved payment.
     * @throws IllegalArgumentException if the request object is null.
     * @throws OrderNotFoundException   if no order is found with the given ID.
     * @throws UserNotFoundException    if no user is found with the given ID.
     */
    @Transactional
    public PaymentResponse savePayment(PaymentRequest paymentRequest) {
        return PaymentResponse.fromEntity(paymentRepository.save(buildPayment(paymentRequest)));
    }


    /**
     * Deletes a payment by its ID.
     *
     * @param paymentId The ID of the payment to delete.
     * @throws IllegalArgumentException if the payment ID is null or invalid.
     * @throws PaymentNotFoundException if no payment is found with the given ID.
     */
    public void deletePayment(Long paymentId) {
        serviceValidator.throwIfIdIsNotValid(paymentId, ErrorMessages.INVALID_PAYMENT_ID);
        paymentRepository.delete(getPaymentOrThrow(paymentId, ErrorMessages.INVALID_PAYMENT_ID));
    }

    /**
     * Updates an existing payment with new details.
     *
     * @param paymentRequest The request object containing updated payment details.
     * @return A response DTO representing the updated payment.
     * @throws IllegalArgumentException if the request is null or contains an invalid ID.
     * @throws PaymentNotFoundException if no payment is found with the given ID.
     */
    @Transactional
    public PaymentResponse updatePayment(PaymentRequest paymentRequest) {
        throwIfRequestIsNull(paymentRequest);
        throwIfIdIsInvalid(paymentRequest.getPaymentId(), ErrorMessages.INVALID_PAYMENT_ID);

        Order order = orderService.getOrderEntity(paymentRequest.getOrderId());

        Payment existingPayment = getPaymentOrThrow(paymentRequest.getPaymentId(), ErrorMessages.PAYMENT_NOT_FOUND);
        existingPayment.setPaymentStatus(paymentRequest.getPaymentStatus());
        existingPayment.setTotalAmount(paymentRequest.getTotalAmount());
        existingPayment.setOrder(order);

        return PaymentResponse.fromEntity(paymentRepository.save(existingPayment));
    }

    /**
     * Builds a Payment entity from a PaymentRequest.
     *
     * @param paymentRequest The request object containing payment details.
     * @return A new Payment entity.
     * @throws IllegalArgumentException if the request object is null.
     * @throws OrderNotFoundException   if the order is not found.
     * @throws UserNotFoundException    if the user is not found.
     */
    private Payment buildPayment(PaymentRequest paymentRequest) {
        throwIfRequestIsNull(paymentRequest);

        Order order = orderService.getOrderEntity(paymentRequest.getOrderId());
        User user = userService.getUserEntity(paymentRequest.getUserId());
        return Payment.builder()
                .totalAmount(paymentRequest.getTotalAmount())
                .paymentStatus(paymentRequest.getPaymentStatus())
                .order(order)
                .user(user)
                .build();
    }
//
//    /**
//     * Validates that the given PaymentRequest is not null.
//     *
//     * @param paymentRequest The request to validate.
//     * @throws IllegalArgumentException if the request is null.
//     */
//    private void throwIfRequestIsNull(PaymentRequest paymentRequest) {
//        if (paymentRequest == null) {
//            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.PAYMENT_REQUEST_IS_NULL));
//        }
//    }
//
//    /**
//     * Validates that the given ID is not null or less than or equal to zero.
//     *
//     * @param id      The ID to validate.
//     * @param message The message key used if the validation fails.
//     * @throws IllegalArgumentException if the ID is null or invalid.
//     */
//    private void throwIfIdIsInvalid(Long id, String message) {
//        if (id == null || id <= 0) {
//            throw new IllegalArgumentException(messageService.getMessage(message, id));
//        }
//    }

    /**
     * Retrieves a Payment entity by ID or throws an exception if not found.
     *
     * @param id      The ID of the payment to retrieve.
     * @param message The message key used if the payment is not found.
     * @return The retrieved Payment entity.
     * @throws PaymentNotFoundException if no payment is found with the given ID.
     */
    private Payment getPaymentOrThrow(Long id, String message) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(messageService.getMessage(message, id)));
    }
}
