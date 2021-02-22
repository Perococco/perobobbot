package perobobbot.backend.i18n;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;

import java.util.Locale;

/**
 * A dictionary used for i18n
 */
public interface Dictionary {

    /**
     * @return the locale this dictionary is for
     */
    @NonNull Locale getLocale();

    /**
     * @return true if this dictionary is empty
     */
    boolean isEmpty();

    /**
     * @return the complete dictionary (a map of key values)
     */
    @NonNull ImmutableMap<String,String> getValues();

    /**
     * @param key the key to translate
     * @return the translation associated with the provided key
     */
    @NonNull String getValue(@NonNull String key);

}
