package perococco.perobobbot.sound;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.lang.FileName;
import perobobbot.lang.PerobobbotException;
import perobobbot.sound.SoundResolver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Log4j2
public class SoundFileResolver implements SoundResolver {

    private final @NonNull Path soundDirectory;

    private static final Set<String> SUPPORTED_EXTENSION = Set.of(
            "wav"
    );

    @Override
    public @NonNull Optional<URL> resolveSound(@NonNull String name) {
        return SUPPORTED_EXTENSION.stream()
                                  .map(extension -> name + "."+extension)
                                  .map(soundDirectory::resolve)
                                  .filter(Files::isRegularFile)
                                  .map(this::toUrl)
                                  .findFirst();
    }

    @Override
    public @NonNull ImmutableSet<String> getAvailableSounds() {
        try {
            return Files.list(soundDirectory)
                        .filter(Files::isRegularFile)
                        .map(FileName::fromPath)
                        .filter(f -> SUPPORTED_EXTENSION.contains(f.getExtension()))
                        .map(FileName::getNameWithoutExtension)
                        .collect(ImmutableSet.toImmutableSet());
        } catch (IOException e) {
            LOG.error("Fail to list all sounds", e);
            return ImmutableSet.of();
        }
    }

    private @NonNull URL toUrl(@NonNull Path path) {
        try {
            return path.toUri().toURL();
        } catch (MalformedURLException e) {
            throw new PerobobbotException("Path to URL failed unexpectedly", e);
        }

    }
}
