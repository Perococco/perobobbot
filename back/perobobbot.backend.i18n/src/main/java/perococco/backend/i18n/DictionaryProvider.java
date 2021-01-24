package perococco.backend.i18n;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.backend.i18n.Dictionary;

import java.util.Locale;

public interface DictionaryProvider {

    @NonNull ImmutableList<Locale> getAvailableLocales();

    @NonNull Dictionary getDictionary(@NonNull Locale locale);

}
