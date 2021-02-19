package perobobbot.fx;

import lombok.NonNull;

import java.util.Locale;

public interface FXLoader {

    @NonNull
    FXLoadingResult load(@NonNull Locale locale);

    default FXLoadingResult load() {
        return load(Locale.getDefault());
    }

    default Object loadAndGetRoot() {
        return load().getRoot();
    }

    default Object loadAndGetRoot(@NonNull Locale locale) {
        return load(locale).getRoot();
    }

    @NonNull
    FXLoader cached();
}
