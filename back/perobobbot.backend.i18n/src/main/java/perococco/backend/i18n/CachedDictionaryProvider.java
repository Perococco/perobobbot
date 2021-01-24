package perococco.backend.i18n;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.backend.i18n.Dictionary;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RequiredArgsConstructor
public class CachedDictionaryProvider implements DictionaryProvider {

    private final @NonNull DictionaryProvider delegate;

    private final @NonNull Map<Locale, Dictionary> cache = Collections.synchronizedMap(new HashMap<>());

    @Override
    public @NonNull ImmutableList<Locale> getAvailableLocales() {
        return delegate.getAvailableLocales();
    }

    @Override
    public @NonNull Dictionary getDictionary(@NonNull Locale locale) {
        return cache.computeIfAbsent(locale, delegate::getDictionary);
    }
}
