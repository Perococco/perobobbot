package perobobbot.i18n;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class I18nEntity {

    @NonNull
    @Getter
    private final String i18nKey;

    @NonNull
    private final Dictionary dictionary;

    @NonNull
    public LocalizedString getLocalizedString() {
        return dictionary.localizedString(getI18nKey());
    }

}
