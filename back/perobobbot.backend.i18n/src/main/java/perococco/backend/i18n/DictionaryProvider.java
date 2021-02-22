package perococco.backend.i18n;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.backend.i18n.Dictionary;

import java.util.Locale;

public interface DictionaryProvider {

    /**
     * @return the list of supported {@link Locale}
     */
    @NonNull ImmutableList<Locale> getAvailableLocales();

    /**
     * @param locale a locale
     * @return the dictionary for the requested {@link Locale}
     */
    @NonNull Dictionary getDictionary(@NonNull Locale locale);

}
