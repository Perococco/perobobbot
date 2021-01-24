package perobobbot.fx;

import lombok.NonNull;

import java.util.Locale;

public interface FXLoader {

    @NonNull
    FXLoadingResult load(@NonNull Locale locale);

    default FXLoadingResult load() {
        return load(Locale.getDefault());
    }

    @NonNull
    FXLoader cached();
}
