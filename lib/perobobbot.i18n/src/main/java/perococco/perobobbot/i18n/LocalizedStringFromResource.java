package perococco.perobobbot.i18n;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import perobobbot.i18n.LocalizedString;

import java.util.*;
import java.util.function.Function;

/**
 * @author Perococco
 */
@Log4j2
public class LocalizedStringFromResource implements LocalizedString {

    @NonNull
    @Getter
    private final ResourceReference resourceReference;

    @NonNull
    private final Function<? super Locale, ? extends ResourceBundle> resourceBundleProviders;

    @NonNull
    private final ParametersTranslator parametersTranslator;

    public LocalizedStringFromResource(
            @NonNull Function<? super Locale,? extends ResourceBundle> resourceBundleProviders,
            @NonNull ResourceReference resourceReference,
            @NonNull ImmutableList<Object> parameters) {
        this.resourceBundleProviders = resourceBundleProviders;
        this.resourceReference = resourceReference;
        this.parametersTranslator = ParametersTranslator.create(parameters);
    }

    @NonNull
    public String getValue(@NonNull Locale locale) {
        return findValue(locale).orElseGet(() -> resourceReference.getNotFoundValuePlaceholder(locale));
    }

    @NonNull
    private Optional<String> findValue(@NonNull Locale locale) {
        try {
            final ResourceBundle resourceBundle = resourceBundleProviders.apply(locale);
            if (resourceBundle != null && resourceBundle.containsKey(resourceReference.getI18nKey())) {
                final String value = String.valueOf(resourceBundle.getObject(resourceReference.getI18nKey()));
                final String message = applyArguments(value, locale);
                return Optional.of(message);
            }
            return Optional.empty();
        } catch (MissingResourceException msr) {
            return Optional.empty();
        }
    }

    @Override
    public @NonNull boolean hasValue(@NonNull Locale locale) {
        return findValue(locale).isPresent();
    }

    @NonNull
    private String applyArguments(@NonNull String message, @NonNull Locale locale) {
        final Object[] parameters = parametersTranslator.translate(locale);
        if (parameters.length == 0) {
            return message;
        }
        try {
            return String.format(message, parameters);
        } catch (IllegalFormatException e) {
            return resourceReference.getErrorPlaceholder(locale,e);
        }
    }

    @Override
    public String toString() {
        return getValue();
    }


}
