package pl.galushop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.galushop.component.MessageService;
import pl.galushop.util.ServiceValidator;
import pl.galushop.component.CacheConstants;
import pl.galushop.component.EmailConstants;
import pl.galushop.component.ErrorMessages;
import pl.galushop.dto.VerificationToken;
import pl.galushop.exception.MailSendException;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final MessageService messageService;
    private final CacheManager cacheManager;
    private final ServiceValidator serviceValidator;

    private final SecureRandom secureRandom = new SecureRandom();


    @Async
    public void sendEmail(String email) {
        String emailActiveCode = generateActiveCode();

        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(15);
        VerificationToken token = new VerificationToken(emailActiveCode, expiresAt);

        var cache = cacheManager.getCache(CacheConstants.VERIFICATION_CODE_CACHE);
        if (cache != null) {
            cache.put(email, token);
        }

        SimpleMailMessage message = new SimpleMailMessage();
        String text = messageService.getMessage(EmailConstants.EMAIL_TEXT, emailActiveCode);

        message.setTo(email);
        message.setFrom(messageService.getMessage(EmailConstants.EMAIL_FORM));
        message.setSubject(messageService.getMessage(EmailConstants.EMAIL_SUBJECT));
        message.setText(text);

        try {
            javaMailSender.send(message);
            log.info("Verification email successfully sent to: {}", email);
        } catch (MailException e) {
            log.error("Failed to send verification email to {}: {}", email, e.getMessage());
            throw new MailSendException(messageService.getMessage(ErrorMessages.EMAIL_SEND_EXCEPTION));
        }
    }

    public VerificationToken getVerificationToken(String email) {
        serviceValidator.throwIfEmailIsInvalid(email, ErrorMessages.EMAIL_IS_INVALID);

        var cache = cacheManager.getCache(CacheConstants.VERIFICATION_CODE_CACHE);
        if (cache != null) {
            return cache.get(email, VerificationToken.class);
        }
        return null;
    }

    public void evictVerificationToken(String email) {
        var cache = cacheManager.getCache(CacheConstants.VERIFICATION_CODE_CACHE);
        if (cache != null) {
            cache.evict(email);
        }
    }

    private String generateActiveCode() {
        int activeCode = secureRandom.nextInt(9000) + 1000;
        return String.valueOf(activeCode);
    }
}
