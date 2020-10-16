package perobobbot.common.sound;

import lombok.NonNull;

import java.net.URL;
import java.util.UUID;

public interface SoundRegistry {

    @NonNull
    UUID registerSoundResource(@NonNull URL soundPath);

    void unregisterSoundResource(@NonNull UUID uuid);


}
