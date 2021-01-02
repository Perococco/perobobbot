package perobobbot.pause;

import lombok.NonNull;
import perobobbot.extension.OverlayExtension;
import perobobbot.lang.Bot;
import perobobbot.overlay.api.Overlay;
import perobobbot.pause.spring.PauseExtensionFactory;

import java.time.Duration;

public class PauseExtension extends OverlayExtension {

    public PauseExtension(@NonNull Overlay overlay) {
        super(PauseExtensionFactory.NAME,overlay);
    }

    public void startPause(@NonNull Duration duration) {
        attachClient(new PauseOverlayClient(duration));
    }

    public void stopPause() {
        detachClient();
    }
}
