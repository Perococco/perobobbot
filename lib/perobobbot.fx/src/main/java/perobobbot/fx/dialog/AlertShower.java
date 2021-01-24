package perobobbot.fx.dialog;

import lombok.NonNull;
import perobobbot.i18n.Dictionary;
import perococco.perobobbot.fx.PeroAlertShower;

public interface AlertShower {

    @NonNull
    static AlertShower create(@NonNull Dictionary dictionary) {
        return new PeroAlertShower(dictionary);
    }

    void showAlert(@NonNull AlertInfo parameters);

    void showAlertAndWait(@NonNull AlertInfo parameters);

}
