package perococco.backend.i18n;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.backend.i18n.Dictionary;

import java.util.Locale;

@RequiredArgsConstructor
public class EmptyDictionary implements Dictionary {

    @NonNull
    @Getter
    private final Locale locale;

    @Override
    public @NonNull ImmutableMap<String, String> getValues() {
        return ImmutableMap.of();
    }

    @Override
    public @NonNull String getValue(@NonNull String key) {
        return key;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
