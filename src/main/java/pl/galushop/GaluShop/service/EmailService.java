package pl.galushop.GaluShop.service;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    public String emailActiveCode;

    @Async
    public void sendEmail(String email) {
        if (email != null && !email.isBlank()) {
            SimpleMailMessage message = new SimpleMailMessage();
            String text = "Twój kod aktywacyjny do GaluShop to: " + emailActiveCode;

            message.setTo(email);
            message.setFrom("projektkoncowymichal@gmail.com");
            message.setSubject("Kod aktywacyjny GaluShop");
            message.setText(text);

            javaMailSender.send(message);
        } else {
            System.out.println("Problem with sending email");
        }
    }

    public String emailCodeValue() {
        emailActiveCode = generateActiveCode();
        return emailActiveCode;
    }

    private String generateActiveCode() {
        Random random = new Random();
        return String.valueOf(random.nextInt(1000, 9999));
    }
}
