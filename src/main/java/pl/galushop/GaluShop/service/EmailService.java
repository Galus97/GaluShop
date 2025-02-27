package pl.galushop.GaluShop.service;


import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.component.MessageService;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final MessageService messageService;
    private final CacheManager cacheManager;

    @Async
    public void sendEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(messageService.getMessage("error.invalidEmailAddress", email));
        }
        String emailActiveCode = generateActiveCode();
        cacheManager.getCache("verificationCodes").put(email, emailActiveCode);

        SimpleMailMessage message = new SimpleMailMessage();
        String text = messageService.getMessage("email.text", emailActiveCode);

        message.setTo(email);
        message.setFrom(messageService.getMessage("email.from"));
        message.setSubject(messageService.getMessage("email.subject"));
        message.setText(text);

        javaMailSender.send(message);
    }

    @Cacheable(value = "verificationCodes", key = "#email")
    public String getVerificationCode(String email){
        return null;
    }

    private String generateActiveCode() {
        Random random = new Random();
        return String.valueOf(random.nextInt(1000, 9999));
    }
}
