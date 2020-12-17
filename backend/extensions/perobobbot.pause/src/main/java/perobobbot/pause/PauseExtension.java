package perobobbot.pause;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.extension.OverlayExtension;
import perobobbot.lang.Subscription;
import perobobbot.overlay.api.Overlay;
import perobobbot.pause.spring.PauseExtensionFactory;

import java.time.Duration;

public class PauseExtension extends OverlayExtension {

    @Getter
    private final @NonNull String userId;

    public PauseExtension(@NonNull String userId, @NonNull Overlay overlay) {
        super(PauseExtensionFactory.NAME,overlay);
        this.userId = userId;
    }

    public void startPause(@NonNull Duration duration) {
        attachClient(new PauseOverlayClient(duration));
    }

    public void stopPause() {
        detachClient();
    }
}
