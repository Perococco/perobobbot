package perobobbot.common.sound;

import lombok.NonNull;
import perococco.perobobbot.common.sound.PerococcoSoundManager;

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.UUID;

public interface SoundManager extends SoundRegistry {

    int getSampleRate();

    int getNbChannels();

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
