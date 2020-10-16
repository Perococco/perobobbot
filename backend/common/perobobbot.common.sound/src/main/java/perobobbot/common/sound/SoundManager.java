package perobobbot.common.sound;

import lombok.NonNull;
import perococco.perobobbot.common.sound.PerococcoSoundManager;

import java.net.URL;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.UUID;

public interface SoundManager {

    int getSampleRate();

    int getNbChannels();

    @NonNull
    UUID registerSoundResource(@NonNull URL soundPath);

    void unregisterSoundResource(@NonNull UUID uuid);

    @NonNull
    Optional<Sound> createSound(@NonNull UUID uuid);

    @NonNull
    static SoundManager create(int sampleRate) {
        return ServiceLoader.load(Factory.class)
                     .findFirst()
                     .orElseGet(() -> PerococcoSoundManager::create)
                     .create(sampleRate);
    }

    interface Factory {
        @NonNull
        SoundManager create(int frameRate);
    }

}
