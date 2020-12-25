package perobobbot.pause;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.extension.OverlayExtension;
import perobobbot.lang.Bot;
import perobobbot.lang.Subscription;
import perobobbot.overlay.api.Overlay;
import perobobbot.pause.spring.PauseExtensionFactory;

import java.time.Duration;

public class PauseExtension extends OverlayExtension {

    @Getter
    private final @NonNull Bot bot;

    public PauseExtension(@NonNull Bot bot, @NonNull Overlay overlay) {
        super(PauseExtensionFactory.NAME,overlay);
        this.bot = bot;
    }

    public void startPause(@NonNull Duration duration) {
        attachClient(new PauseOverlayClient(duration));
    }

    public void stopPause() {
        detachClient();
    }
}
