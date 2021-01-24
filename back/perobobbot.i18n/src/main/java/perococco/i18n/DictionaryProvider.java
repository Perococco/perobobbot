package perococco.i18n;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.i18n.Dictionary;

import java.util.Locale;

public interface DictionaryProvider {

    @NonNull ImmutableList<Locale> getAvailableLocales();

    @NonNull Dictionary getDictionary(@NonNull Locale locale);

}
