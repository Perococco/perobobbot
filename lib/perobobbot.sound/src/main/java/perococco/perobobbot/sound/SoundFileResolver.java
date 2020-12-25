package perococco.perobobbot.sound;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.PerobobbotException;
import perobobbot.sound.SoundResolver;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class SoundFileResolver implements SoundResolver {

    private final @NonNull Path soundDirectory;

    private static final Set<String> SUPPORTED_SUFFIX = Set.of(
            ".wav"
    );

    @Override
    public @NonNull Optional<URL> resolveSound(@NonNull String name) {
        return SUPPORTED_SUFFIX.stream()
                                          .map(suffix -> name + suffix)
                                          .map(soundDirectory::resolve)
                                          .filter(Files::isRegularFile)
                                          .map(this::toUrl)
                                          .findFirst();
    }

    private @NonNull URL toUrl(@NonNull Path path) {
        try {
            return path.toUri().toURL();
        } catch (MalformedURLException e) {
            throw new PerobobbotException("Path to URL failed unexpectedly",e);
        }

    }
}
