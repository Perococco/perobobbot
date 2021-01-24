package perobobbot.i18n;

import lombok.NonNull;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Perococco
 */
public interface Dictionary {

    @NonNull
    ResourceBundle getResourceBundle(@NonNull Locale locale);

    /**
     * @return the localized string associated with the provided <code>i18nKey</code>
     */
    @NonNull
    LocalizedString localizedString(@NonNull String i18nKey);

    /**
     * @return the localized string with parameters associated with the provided <code>i18nKey</code>
     */
    @NonNull
    LocalizedString localizedString(@NonNull String i18nKey, @NonNull Object... parameters);

    /**
     * @return the value of the localized string associated with the provided i18nkey in the default Locale
     */
    @NonNull
    default String value(@NonNull String i18nKey) {
        return localizedString(i18nKey).getValue();
    }

    /**
     * @return the value of the localized string with parameters associated with the provided i18nKey in the default Locale
     */
    @NonNull
    default String value(@NonNull String i18nKey, @NonNull Object... parameters) {
        return localizedString(i18nKey, parameters).getValue();
    }

    /**
     * @return the value of the localized string associated with the provided i18nKey in the provided Locale
     */
    @NonNull
    default String value(@NonNull Locale locale, @NonNull String i18nKey) {
        return localizedString(i18nKey).getValue(locale);
    }

    /**
     * @return the value of the localized string with parameters associated with the provided i18nKey in the provided Locale
     */
    @NonNull
    default String value(@NonNull Locale locale, @NonNull String i18nKey, @NonNull Object... parameters) {
        return localizedString(i18nKey, parameters).getValue(locale);
    }

    @NonNull
    Dictionary withPrefix(@NonNull String i18nPrefix);

}
