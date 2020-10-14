package perobobbot.common.sound;

import lombok.NonNull;

import javax.sound.sampled.AudioFormat;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;

public interface SoundManager {

    @NonNull
    UUID registerSoundResource(@NonNull URL soundPath);

    void unregisterSoundResource(@NonNull UUID uuid);

    @NonNull
    Optional<Sound> createSound(@NonNull UUID uuid);

}
