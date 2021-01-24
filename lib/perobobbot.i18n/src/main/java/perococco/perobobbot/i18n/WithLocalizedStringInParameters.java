package perococco.perobobbot.i18n;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.i18n.LocalizedString;

import java.util.Locale;

/**
 * @author Perococco
 */
@RequiredArgsConstructor
public class WithLocalizedStringInParameters implements ParametersTranslator {

    @NonNull
    private final ImmutableList<Object> parameters;

    @Override
    public @NonNull Object[] translate(@NonNull Locale locale) {
        return new Translator(locale).translate();
    }

    @RequiredArgsConstructor
    private class Translator {

        @NonNull
        private final Locale locale;

        public Object[] translate() {
            return parameters.stream()
                             .map(this::transformObjectParameters)
                             .toArray();
        }

        private Object transformObjectParameters(@NonNull Object parameter) {
            if (parameter instanceof LocalizedString) {
                return ((LocalizedString) parameter).getValue(locale);
            }
            return parameter;
        }

    }
}
