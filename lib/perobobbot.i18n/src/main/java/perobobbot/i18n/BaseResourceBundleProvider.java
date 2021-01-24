package perobobbot.i18n;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.spi.AbstractResourceBundleProvider;

/**
 * @author Perococco
 */
public abstract class BaseResourceBundleProvider extends AbstractResourceBundleProvider {

    public BaseResourceBundleProvider() {
        super("java.properties");
    }

    @Override
    public ResourceBundle getBundle(String baseName, Locale locale) {
        ResourceBundle bundle = super.getBundle(baseName, locale);
        if (bundle == null && !locale.getCountry().isEmpty()) {
            bundle = super.getBundle(baseName, new Locale(locale.getLanguage()));
        }
        if (bundle == null) {
            bundle = super.getBundle(baseName, Locale.ROOT);
        }
        return bundle;
    }
}
