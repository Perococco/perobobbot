package perococco.perobobbot.frontfx.gui.style;

import lombok.NonNull;
import perobobbot.lang.Theme;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class ThemeConstructor {

    private static final String BASE_CSS_DIRECTORY = System.getenv("BASE_CSS_DIR");

    @NonNull
    public Theme create(@NonNull String name) {
        final String themeUrl = getPossibleThemePath(name)
                .map(p -> fromDirectory(name, p))
                .orElseGet(() -> fromResource(name));

        return Theme.create(name,themeUrl);
    }

    @NonNull
    private Optional<Path> getPossibleThemePath(@NonNull String themeName) {
        if (BASE_CSS_DIRECTORY == null) {
            return Optional.empty();
        }
        final Path path = Path.of(BASE_CSS_DIRECTORY)
                              .resolve(themeName.toLowerCase())
                              .resolve("theme.css");

        if (Files.exists(path)) {
            return Optional.of(path);
        }
        return Optional.empty();
    }

    private static String fromResource(@NonNull String name) {
        return ThemeConstructor.class.getResource(name.toLowerCase() + "/theme.css").toExternalForm();
    }

    public static String fromDirectory(@NonNull String name, @NonNull Path rootCssPath) {
        try {
            return rootCssPath.toAbsolutePath().toUri().toURL().toExternalForm();
        } catch (MalformedURLException e) {
            throw new RuntimeException("BUG, valid paths always return valid URLs");
        }
    }

    private String getThemeUrl(@NonNull String themeName) {
        return Optional.ofNullable(BASE_CSS_DIRECTORY)
                       .map(Path::of)
                       .map(p -> p.resolve(themeName.toLowerCase()))
                       .map(p -> p.resolve("theme.css"))
                       .filter(p -> Files.exists(p))
                       .map(p -> fromDirectory(themeName, p))
                       .orElseGet(() -> fromResource(themeName));

    }
}
