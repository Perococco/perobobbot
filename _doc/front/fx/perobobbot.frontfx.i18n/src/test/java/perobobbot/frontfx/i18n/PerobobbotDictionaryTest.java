package perobobbot.frontfx.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

public class PerobobbotDictionaryTest {


    @Test
    public void resourceShouldBeFound() {
        final ResourceBundle bundle = PerobobbotDictionary.INSTANCE.getResourceBundle(Locale.ENGLISH);
        final String value = bundle.getString("_lit.Login");
        Assertions.assertEquals("Login",value);
    }
}
