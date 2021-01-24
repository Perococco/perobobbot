package perobobbot.fx.dialog;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.i18n.Dictionary;
import perobobbot.i18n.LocalizedString;

import java.util.Optional;

public class AlertInfo {

    @NonNull
    private final Throwable error;

    @NonNull
    @Getter
    private final String msgI18nKey;

    @NonNull
    @Getter
    private final Object[] parameters;

    public AlertInfo(@NonNull Throwable error, @NonNull String msgI18nKey) {
        this.error = error;
        this.msgI18nKey = msgI18nKey;
        this.parameters = new Object[0];
    }

    public AlertInfo(@NonNull Throwable error, @NonNull String msgI18nKey, @NonNull Object... parameters) {
        this.error = error;
        this.msgI18nKey = msgI18nKey;
        this.parameters = parameters;
    }

    @NonNull
    public Optional<Throwable> getError() {
        return Optional.ofNullable(error);
    }

    @NonNull
    public LocalizedString getMessage(@NonNull Dictionary dictionary) {
        if (parameters.length == 0) {
            return dictionary.localizedString(msgI18nKey);
        }
        return dictionary.localizedString(msgI18nKey,parameters);
    }

}
