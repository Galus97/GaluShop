package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import pl.galushop.GaluShop.component.MessageService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    @Mock
    JavaMailSender javaMailSender;
    @Mock
    MessageService messageService;
    @Mock
    CacheManager cacheManager;
    @Mock
    Cache cache;
    @InjectMocks
    EmailService emailService;


    @Test
    void givenValidEmail_whenSendEmail_thenSendMessageAndCacheCode() {
        //Arrange
        when(cacheManager.getCache("verificationCodes")).thenReturn(cache);
        //Act
        emailService.sendEmail("valid@gmail.com");
        //Assert
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
        verify(cache, times(1)).put(eq("valid@gmail.com"), anyString());
    }

    @Test
    void givenInvalidEmail_whenSendEmail_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            emailService.sendEmail("");
        });
        verifyNoInteractions(javaMailSender);
        verifyNoInteractions(cache);
    }
    @Test
    void givenNullEmail_whenSendEmail_thenThrowException(){
        assertThrows(IllegalArgumentException.class, () -> {
            emailService.sendEmail(null);
        });
        verifyNoInteractions(javaMailSender);
        verifyNoInteractions(cache);
    }

    @Test
    void givenValidEmail_whenGetVerificationCode_thenReturnCode(){
        //Arrange
        when(cacheManager.getCache("verificationCodes")).thenReturn(cache);
        when(cache.get("valid@gmail.com", String.class)).thenReturn("1234");
        //Act
        String verificationCode = emailService.getVerificationCode("valid@gmail.com");
        //Assert
        assertEquals("1234", verificationCode);
    }

    @Test
    void givenInvalidEmail_whenGetVerificationCode_thenThrowException(){
        assertThrows(IllegalArgumentException.class, () -> {
            emailService.getVerificationCode("");
        });
        verifyNoInteractions(javaMailSender);
        verifyNoInteractions(cache);
    }


}