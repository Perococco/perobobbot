package perobobbot.fx;

import javafx.beans.value.ObservableStringValue;
import lombok.NonNull;

public interface FXDictionary {

    /**
     * @return the localized string associated with the provided <code>i18nKey</code>
     */
    @NonNull
    ObservableStringValue localizedString(@NonNull String i18nKey);

    /**
     * @return the localized string with parameters associated with the provided <code>i18nKey</code>
     */
    @NonNull
    ObservableStringValue localizedString(@NonNull String i18nKey, @NonNull Object... parameters);


}
