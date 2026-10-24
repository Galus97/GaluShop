package pl.galushop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.util.ServiceValidator;
import pl.galushop.component.ErrorMessages;

import java.security.SecureRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private static final String VERIFICATION_CODE_CACHE = "verificationCodes"; // Stała dedykowana do cache

    private final JavaMailSender javaMailSender;
    private final MessageService messageService;
    private final CacheManager cacheManager;
    private final ServiceValidator serviceValidator;


    @Async
    public void SendEmail(String email) {
        String emailActiveCode = generateActiveCode();
        // TODO
    }

    public String getVerificationCode(String email) {
        serviceValidator.throwIfEmailIsInvalid(email, ErrorMessages.EMAIL_IS_INVALID);

        var cache = cacheManager.getCache(VERIFICATION_CODE_CACHE);
        if (cache != null) {
            return cache.get(email, String.class);
        }
        return null;
    }


    private String generateActiveCode() {
        int activeCode = new SecureRandom().nextInt(9000) + 1000;
        return String.valueOf(activeCode);
    }
}
