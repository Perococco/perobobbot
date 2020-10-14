package perobobbot.common.sound;

import lombok.NonNull;

import java.net.URL;
import java.util.Optional;
import java.util.UUID;

public interface SoundManager {

    @NonNull
    UUID registerSound(@NonNull URL soundPath);

    void unregisterSound(@NonNull UUID uuid);

    @NonNull
    Optional<Sound> createSound(@NonNull UUID uuid);

}
