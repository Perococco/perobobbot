package perobobbot.overlay;

import lombok.NonNull;

import java.util.UUID;

public interface SoundContext {

    @NonNull
    SoundExecution playSound(@NonNull UUID soundId);
}
