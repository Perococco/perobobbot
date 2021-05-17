package perococco.perobobbot.fx;

import javafx.fxml.FXMLLoader;
import javafx.util.BuilderFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.fx.ControllerFactory;
import perobobbot.fx.FXLoader;
import perobobbot.fx.FXLoadingResult;
import perobobbot.i18n.Dictionary;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.Locale;

@RequiredArgsConstructor
public class DefaultFXLoader implements FXLoader {

    @NonNull
    private final ControllerFactory controllerFactory;

    @NonNull
    private final BuilderFactory builderFactory;

    private final Dictionary dictionary;

    private final URL fxmlFile;

    @Override
    public @NonNull FXLoadingResult load(@NonNull Locale locale) {
        final FXMLLoader loader = createAndPrepareLoader(this.fxmlFile, locale);
        try {
            loader.load();
            return new FXLoadingResult(loader.getController(), loader.getRoot());
        } catch (IOException e) {
            e.printStackTrace();throw new UncheckedIOException(e);
        }
    }

    private FXMLLoader createAndPrepareLoader(@NonNull URL fxmlFile, @NonNull Locale locale) {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(fxmlFile);
        loader.setControllerFactory(controllerFactory.asCallbackForFXMLLoader());
        loader.setBuilderFactory(builderFactory);
        loader.setResources(dictionary.getResourceBundle(locale));
        return loader;
    }

    @Override
    public @NonNull FXLoader cached() {
        return new CachedFXLoader(this);
    }
}
