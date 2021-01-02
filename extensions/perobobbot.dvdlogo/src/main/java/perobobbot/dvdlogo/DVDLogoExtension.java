package perobobbot.dvdlogo;

import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.extension.OverlayExtension;
import perobobbot.overlay.api.Overlay;

public class DVDLogoExtension extends OverlayExtension {

    public static final String NAME = "dvdlogo";

    public DVDLogoExtension(@NonNull Overlay overlay) {
        super(NAME,overlay);
    }

    @Synchronized
    public void startOverlay() {
        if (!isEnabled() || isClientAttached()) {
            return;
        }
        this.attachClient(new DVDLogoOverlay());
    }

    @Synchronized
    public void stopOverlay() {
        this.detachClient();
    }
}
