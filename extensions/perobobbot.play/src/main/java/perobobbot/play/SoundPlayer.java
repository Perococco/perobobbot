package perobobbot.play;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import perobobbot.overlay.api.OverlayClient;
import perobobbot.overlay.api.OverlayIteration;
import perobobbot.overlay.api.SoundExecution;

import java.util.UUID;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Log4j2
public class SoundPlayer implements OverlayClient {

    private final BlockingDeque<UUID> soundToPlay = new LinkedBlockingDeque<>();

    private SoundExecution inProgressSound = null;

    @Override
    public void render(@NonNull OverlayIteration iteration) {
        if (inProgressSound != null && !inProgressSound.isDone()) {
            return;
        }
        final var uuid = getNextSoundToPlay();
        if (uuid == null) {
            return;
        }
        LOG.info("Play sound {}",uuid);
        inProgressSound = iteration.getSoundContext().playSound(uuid);
    }

    private UUID getNextSoundToPlay() {
        return soundToPlay.poll();
    }

    public void addToQueue(@NonNull UUID uuidOfSoundToPlay) {
        soundToPlay.add(uuidOfSoundToPlay);
    }
}
