package perobobbot.play;

import lombok.NonNull;

import java.net.URL;
import java.util.Optional;

public interface SoundResolver {

    @NonNull Optional<URL> resolveSound(@NonNull String name);
}
