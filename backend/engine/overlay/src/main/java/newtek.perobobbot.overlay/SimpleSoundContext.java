package newtek.perobobbot.overlay;

import lombok.NonNull;
import perobobbot.overlay.SoundContext;
import perobobbot.overlay.SoundExecution;

import java.util.UUID;

public class SimpleSoundContext implements SoundContext {

    @Override
    public @NonNull SoundExecution playSound(@NonNull UUID soundId) {
        throw new RuntimeException("Not Implemented");
    }
}
