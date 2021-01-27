package perobobbot.sound;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perococco.perobobbot.sound.SoundFileResolver;

import java.net.URL;
import java.nio.file.Path;
import java.util.Optional;

public interface SoundResolver {

    static SoundResolver soundFileResolver(@NonNull Path soundDirectory) {
        return new SoundFileResolver(soundDirectory);
    }

    @NonNull Optional<URL> resolveSound(@NonNull String name);

    @NonNull ImmutableSet<String> getAvailableSounds();
}
