package perobobbot.play;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import perobobbot.extension.OverlayExtension;
import perobobbot.overlay.api.Overlay;
import perobobbot.sound.SoundNotFound;
import perobobbot.sound.SoundResolver;

@Log4j2
public class PlayExtension extends OverlayExtension {

    public static final String NAME = "play";

    private final SoundResolver soundResolver;

    private final SoundPlayer soundPlayer;

    public PlayExtension(@NonNull Overlay overlay, @NonNull SoundResolver soundResolver) {
        super(NAME, overlay);
        this.soundPlayer = new SoundPlayer();
        this.soundResolver = soundResolver;
    }

    @Override
    protected void onEnabled() {
        super.onEnabled();
        this.attachClient(soundPlayer);
    }

    public void playSound(@NonNull String name) {
        final var overlay = getOverlay();
        final var url = soundResolver.resolveSound(name).orElseThrow(() -> new SoundNotFound(name));
        final var uuid = overlay.registerSoundResource(url);
        LOG.info("Add sound to queue {} - {}", name, uuid);
        soundPlayer.addToQueue(uuid);
    }
}
