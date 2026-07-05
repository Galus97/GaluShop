package pl.galushop.GaluShop.service;


import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.util.ServiceValidator;
import pl.galushop.GaluShop.component.ErrorMessages;
import pl.galushop.GaluShop.component.MessageService;

import java.util.Random;

/**
 * Service responsible for handling email-related operations,
 * such as sending verification codes and retrieving stored codes.
 */
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final MessageService messageService;
    private final CacheManager cacheManager;
    private final ServiceValidator serviceValidator;

    /**
     * Sends an email containing a randomly generated verification code.
     * The code is stored in cache for later validation.
     *
     * @param email the recipient's email address
     * @throws IllegalArgumentException if the provided email is null or blank
     */
    @Async
    public void sendEmail(String email) {
        String emailActiveCode = generateActiveCode();
        cacheManager.getCache(ErrorMessages.VERIFICATION_CODE).put(email, emailActiveCode);

        SimpleMailMessage message = new SimpleMailMessage();
        String text = messageService.getMessage(ErrorMessages.EMAIL_TEXT, emailActiveCode);

        message.setTo(email);
        message.setFrom(messageService.getMessage(ErrorMessages.EMAIL_FORM));
        message.setSubject(messageService.getMessage(ErrorMessages.EMAIL_SUBJECT));
        message.setText(text);

        javaMailSender.send(message);
    }

    /**
     * Retrieves the verification code associated with the given email.
     * If the code is not in cache, returns null.
     *
     * @param email the recipient's email address
     * @return the stored verification code, or null if not found
     */
    @Cacheable(value = ErrorMessages.VERIFICATION_CODE, key = "#email")
    public String getVerificationCode(String email) {
        serviceValidator.throwIfEmailIsInvalid(email, ErrorMessages.EMAIL_IS_INVALID);
        return cacheManager.getCache(ErrorMessages.VERIFICATION_CODE).get(email, String.class);
    }

    /**
     * Generates a random 4-digit verification code.
     *
     * @return a 4-digit numeric code as a String
     */
    private String generateActiveCode() {
        Random random = new Random();
        return String.valueOf(random.nextInt(1000, 9999));
    }
}
