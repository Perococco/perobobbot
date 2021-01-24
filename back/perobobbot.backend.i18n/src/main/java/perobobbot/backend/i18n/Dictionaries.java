package perobobbot.backend.i18n;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perococco.backend.i18n.DictionaryFromResourceProvider;
import perococco.backend.i18n.DictionaryProvider;

import java.util.Locale;

public enum Dictionaries implements DictionaryProvider {
    INSTANCE,
    ;

    private final DictionaryProvider provider = new DictionaryFromResourceProvider();


    @Override
    public @NonNull ImmutableList<Locale> getAvailableLocales() {
        return provider.getAvailableLocales();
    }

    /**
     * @param locale the locale of the requested dictionary
     * @return the dictionary for the provided locale if it exists, an empty dictionary otherwise
     */
    @Override
    public @NonNull Dictionary getDictionary(@NonNull Locale locale) {
        return provider.getDictionary(locale);
    }
}
