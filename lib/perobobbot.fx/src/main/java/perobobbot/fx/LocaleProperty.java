package perobobbot.fx;

import javafx.beans.value.ObservableValueBase;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.i18n.Dictionary;
import perococco.perobobbot.fx.PeroFXDictionary;

import java.util.Locale;

@NoArgsConstructor
public class LocaleProperty extends ObservableValueBase<Locale> {

    @NonNull
    private Locale value = Locale.getDefault();

    public void setLocale(@NonNull Locale locale) {
        Locale.setDefault(locale);
        value = locale;
        fireValueChangedEvent();
    }

    @Override
    public Locale getValue() {
        return value;
    }

    @NonNull
    public FXDictionary wrapDictionary(@NonNull Dictionary dictionary) {
        return new PeroFXDictionary(dictionary, this);
    }
}
