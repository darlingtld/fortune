package fortune.validation;

import common.Utils;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-19.
 */
@Component
public class Validator {

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(Utils.HEADER_MESSAGE);
        return messageSource;
    }
}
