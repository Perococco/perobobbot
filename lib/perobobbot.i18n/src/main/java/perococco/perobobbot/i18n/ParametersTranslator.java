package perococco.perobobbot.i18n;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.i18n.LocalizedString;

import java.util.Locale;

/**
 * @author perococco
 */
public interface ParametersTranslator {

    Object[] EMPTY = new Object[0];

    @NonNull
    Object[] translate(@NonNull Locale locale);

    @NonNull
    static ParametersTranslator create(@NonNull ImmutableList<Object> parameters) {
        if (parameters.isEmpty()) {
            return locale ->  EMPTY;
        }
        else if (parameters.stream().anyMatch(p -> p instanceof LocalizedString)) {
            return new WithLocalizedStringInParameters(parameters);
        } else {
            final Object[] result = parameters.toArray(Object[]::new);
            return l -> result;
        }
    }


}
