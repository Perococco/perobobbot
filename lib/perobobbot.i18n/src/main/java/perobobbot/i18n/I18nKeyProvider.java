package perobobbot.i18n;

import lombok.NonNull;

import java.util.Locale;

public interface I18nKeyProvider {

    @NonNull
    String getI18nKey();

    @NonNull
    default I18nEntity toI18nEntity(@NonNull Dictionary dictionary) {
        return new I18nEntity(this.getI18nKey(),dictionary);
    }

    @NonNull
    default LocalizedString toLocalizedString(@NonNull Dictionary dictionary) {
        return toI18nEntity(dictionary).getLocalizedString();
    }

    @NonNull
    default String getLocalizedValue(@NonNull Dictionary dictionary) {
        return toLocalizedString(dictionary).getValue();
    }

    @NonNull
    default String getLocalizedValue(@NonNull Dictionary dictionary, @NonNull Locale locale) {
        return toLocalizedString(dictionary).getValue(locale);
    }

}
