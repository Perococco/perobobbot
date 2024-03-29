package perococco.backend.i18n;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perobobbot.backend.i18n.Dictionary;

import java.util.Locale;
import java.util.ResourceBundle;

public class DictionaryFromResource implements Dictionary {

    private final Locale locale;

    private final ImmutableMap<String, String> values;

    public DictionaryFromResource(Locale locale) {
        this.locale = locale;
        final var rb = ResourceBundle.getBundle("perococco/backend/i18n/perobobbot", locale);
        this.values = rb.keySet()
                        .stream()
                        .collect(ImmutableMap.toImmutableMap(k -> k, rb::getString));
    }

    @Override
    public @NonNull Locale getLocale() {
        return locale;
    }

    @Override
    public @NonNull ImmutableMap<String, String> getValues() {
        return values;
    }

    @Override
    public @NonNull String getValue(@NonNull String key) {
        return values.get(key);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
