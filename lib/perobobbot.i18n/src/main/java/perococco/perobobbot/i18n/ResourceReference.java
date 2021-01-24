package perococco.perobobbot.i18n;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Locale;

@RequiredArgsConstructor
public class ResourceReference {

    @NonNull
    @Getter
    private final String resourceBasename;

    @NonNull
    @Getter
    private final String i18nKey;

    @NonNull
    public String getNotFoundValuePlaceholder(@NonNull Locale locale) {
        return "??"+getResourceReference(locale)+"??";
    }

    @NonNull
    public String getErrorPlaceholder(@NonNull Locale locale, @NonNull Throwable throwable) {
        return "!!"+getResourceReference(locale)+" : "+throwable.getMessage()+"!!";
    }

    private String getResourceReference(@NonNull Locale locale) {
        return locale+":"+resourceBasename+":"+i18nKey;
    }


}
