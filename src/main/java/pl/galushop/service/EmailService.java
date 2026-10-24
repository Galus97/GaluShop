package pl.galushop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.util.ServiceValidator;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final MessageService messageService;
    private final ServiceValidator serviceValidator;

    @Async
    public void SendEmail(String email) {
        String emailActiveCode = generateActiveCode();
        // TODO
    }


    private String generateActiveCode() {
        int activeCode = new SecureRandom().nextInt(9000) + 1000;
        return String.valueOf(activeCode);
    }
}
