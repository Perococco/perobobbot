package perobobbot.frontfx.i18n;

import lombok.experimental.Delegate;
import perobobbot.i18n.BaseDictionary;
import perobobbot.i18n.BaseResourceBundleProvider;
import perobobbot.i18n.Dictionary;

import java.util.Locale;
import java.util.ResourceBundle;

public enum PerobobbotDictionary implements Dictionary {
    INSTANCE,
    ;

    @Delegate
    private final BaseDictionary dictionary;

    PerobobbotDictionary() {
        this.dictionary = new BaseDictionary(new BaseResourceBundleProvider() {}, PerobobbotDictionary.class.getName());
    }
}
