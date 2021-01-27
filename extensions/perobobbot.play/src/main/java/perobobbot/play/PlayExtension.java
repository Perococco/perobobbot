package perobobbot.play;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import perobobbot.extension.OverlayExtension;
import perobobbot.lang.MessageDispatcher;
import perobobbot.lang.SubscriptionHolder;
import perobobbot.overlay.api.Overlay;
import perobobbot.sound.SoundNotFound;
import perobobbot.sound.SoundResolver;

@Log4j2
public class PlayExtension extends OverlayExtension {

    public static final String NAME = "play";

    private final MessageDispatcher messageDispatcher;

    private final SoundResolver soundResolver;

    private final SoundPlayer soundPlayer;

    private final SubscriptionHolder subscriptionHolder = new SubscriptionHolder();

    public PlayExtension(@NonNull Overlay overlay, @NonNull MessageDispatcher messageDispatcher, @NonNull SoundResolver soundResolver) {
        super(NAME, overlay);
        this.soundPlayer = new SoundPlayer();
        this.messageDispatcher = messageDispatcher;
        this.soundResolver = soundResolver;
    }

    @Override
    protected void onEnabled() {
        super.onEnabled();
        subscriptionHolder.replaceWith(() -> this.messageDispatcher.addPreprocessor(new PlayPreprocessor(this)));
        this.attachClient(soundPlayer);
    }

    @Override
    protected void onDisabled() {
        super.onDisabled();
        subscriptionHolder.unsubscribe();
    }

    public boolean canPlaySound(@NonNull String soundName) {
        return soundResolver.resolveSound(soundName).isPresent();
    }

    public @NonNull ImmutableSet<String> getAvailableSounds() {
        return soundResolver.getAvailableSounds();
    }

    public void playSound(@NonNull String name) {
        final var overlay = getOverlay();
        final var url = soundResolver.resolveSound(name).orElseThrow(() -> new SoundNotFound(name));
        final var uuid = overlay.registerSoundResource(url);
        LOG.info("Add sound to queue {} - {}", name, uuid);
        soundPlayer.addToQueue(uuid);
    }
}
