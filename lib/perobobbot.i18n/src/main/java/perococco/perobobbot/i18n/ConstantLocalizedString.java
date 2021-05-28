package perococco.perobobbot.i18n;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.i18n.LocalizedString;

import java.util.Locale;

/**
 * @author perococco
 */
@RequiredArgsConstructor
public class ConstantLocalizedString implements LocalizedString {

    @NonNull
    private final String value;

    @Override
    public @NonNull String getValue(@NonNull Locale locale) {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean hasValue(@NonNull Locale locale) {
        return true;
    }
}
