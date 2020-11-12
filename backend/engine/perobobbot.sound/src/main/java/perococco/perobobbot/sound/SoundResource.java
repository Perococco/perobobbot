package perococco.perobobbot.sound;

import lombok.NonNull;
import perobobbot.sound.Sound;

public interface SoundResource {

    @NonNull
    Sound createSound();

}
