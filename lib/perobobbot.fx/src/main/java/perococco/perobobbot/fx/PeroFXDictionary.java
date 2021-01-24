package perococco.perobobbot.fx;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableStringValue;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.fx.FXDictionary;
import perobobbot.fx.LocaleProperty;
import perobobbot.i18n.Dictionary;
import perobobbot.i18n.LocalizedString;

@RequiredArgsConstructor
public class PeroFXDictionary implements FXDictionary {

    @NonNull
    private final Dictionary dictionary;

    @NonNull
    private final LocaleProperty localeProperty;

    /**
     * @return the localized string associated with the provided <code>i18nKey</code>
     */
    @NonNull
    @Override
    public ObservableStringValue localizedString(@NonNull String i18nKey) {
        return toObservable(dictionary.localizedString(i18nKey));
    }

    /**
     * @return the localized string with parameters associated with the provided <code>i18nKey</code>
     */
    @NonNull
    @Override
    public ObservableStringValue localizedString(@NonNull String i18nKey, @NonNull Object... parameters) {
        return toObservable(dictionary.localizedString(i18nKey,parameters));
    }

    @NonNull
    private ObservableStringValue toObservable(@NonNull LocalizedString localizedString) {
        return Bindings.createStringBinding(() -> localizedString.getValue(localeProperty.getValue()), localeProperty);
    }

}
