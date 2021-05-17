package perococco.perobobbot.i18n;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.i18n.Dictionary;
import perobobbot.i18n.LocalizedString;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 * @author perococco
 */
@RequiredArgsConstructor
public class PrefixedDictionary implements Dictionary {

    @NonNull
    private final Dictionary delegate;

    @NonNull
    private final String prefix;

    @Override
    public @NonNull LocalizedString localizedString(@NonNull String i18nKey) {
        return delegate.localizedString(prefix + i18nKey);
    }

    @Override
    public @NonNull LocalizedString localizedString(@NonNull String i18nKey, @NonNull Object... parameters) {
        return delegate.localizedString(prefix + i18nKey, parameters);
    }

    @Override
    public @NonNull Dictionary withPrefix(@NonNull String i18nPrefix) {
        return new PrefixedDictionary(delegate,prefix+i18nPrefix);
    }

    @Override
    public @NonNull ResourceBundle getResourceBundle(@NonNull Locale locale) {
        final ResourceBundle resourceBundle = delegate.getResourceBundle(locale);
        return new ResourceBundle() {
            @Override
            protected Object handleGetObject(@NonNull String key) {
                return resourceBundle.getObject(prefix+key);
            }

            @Override
            @NonNull
            public Enumeration<String> getKeys() {
                final Enumeration<String> enumeration = resourceBundle.getKeys();
                final Vector<String> vector = new Vector<>();
                while (enumeration.hasMoreElements()) {
                    final String element = enumeration.nextElement();
                    if (element.startsWith(prefix)) {
                        vector.add(element.substring(prefix.length()));
                    }
                }
                return vector.elements();
            }
        };
    }
}
