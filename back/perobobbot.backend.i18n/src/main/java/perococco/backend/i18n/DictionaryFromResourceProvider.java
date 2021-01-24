package perococco.backend.i18n;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.backend.i18n.Dictionary;

import java.util.Locale;

public class DictionaryFromResourceProvider implements DictionaryProvider {

    @Override
    public @NonNull ImmutableList<Locale> getAvailableLocales() {
        return ImmutableList.of(Locale.FRENCH,Locale.ENGLISH);
    }

    @Override
    public @NonNull Dictionary getDictionary(@NonNull Locale locale) {
        return new DictionaryFromResource(locale);
    }
}
