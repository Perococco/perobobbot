package perobobbot.fx;

import lombok.NonNull;
import perobobbot.i18n.Dictionary;

import java.net.URL;

public interface FXLoaderFactory {

    @NonNull
    FXLoader create(@NonNull URL fxmlFile, @NonNull Dictionary dictionary);

    @NonNull
    FXLoader create(@NonNull Class<?> controllerClass, @NonNull Dictionary dictionary);

    @NonNull
    FXLoader create(@NonNull URL fxmlFile);

    @NonNull
    FXLoader create(@NonNull Class<?> controllerClass);

}
