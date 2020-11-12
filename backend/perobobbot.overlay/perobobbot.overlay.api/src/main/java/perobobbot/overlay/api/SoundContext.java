package perobobbot.overlay.api;

import lombok.NonNull;

import java.util.UUID;

public interface SoundContext {

    /**
     * Play the sound at the next frame rendering.
     * @param soundId the id of the sound. This is the id returns by the
     *                {@link perobobbot.sound.SoundRegistry} implementation
     *                of the {@link Overlay}
     * @return a structure that can be used to control the sound execution
     */
    @NonNull
    SoundExecution playSound(@NonNull UUID soundId);
}
