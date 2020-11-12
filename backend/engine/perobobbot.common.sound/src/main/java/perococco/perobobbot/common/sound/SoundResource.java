package perococco.perobobbot.common.sound;

import lombok.NonNull;
import perobobbot.common.sound.Sound;

public interface SoundResource {

    @NonNull
    Sound createSound();

}
