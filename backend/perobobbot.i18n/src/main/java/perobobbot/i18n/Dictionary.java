package perobobbot.i18n;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;

import java.util.Locale;

public interface Dictionary {

    @NonNull Locale getLocale();

    boolean isEmpty();

    @NonNull ImmutableMap<String,String> getValues();

    @NonNull String getValue(@NonNull String value);

}
