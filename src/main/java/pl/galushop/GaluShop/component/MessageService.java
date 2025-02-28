package pl.galushop.GaluShop.component;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Service component responsible for retrieving localized messages.
 */
@Component
@RequiredArgsConstructor
public class MessageService {
    private final MessageSource messageSource;

    /**
     * Retrieves a localized message based on the given key and parameters.
     *
     * @param key    The message key.
     * @param params Optional parameters to format the message.
     * @return The localized message as a string.
     */
    public String getMessage(String key, Object... params){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, params, locale);
    }
}
