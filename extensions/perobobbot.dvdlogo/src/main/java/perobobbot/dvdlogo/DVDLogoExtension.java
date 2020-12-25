package perobobbot.dvdlogo;

import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.command.CommandBundleLifeCycle;
import perobobbot.extension.ExtensionBase;
import perobobbot.extension.OverlayExtension;
import perobobbot.lang.Bot;
import perobobbot.lang.SubscriptionHolder;
import perobobbot.lang.fp.Function1;
import perobobbot.overlay.api.Overlay;

public class DVDLogoExtension extends OverlayExtension {

    public static final String NAME = "dvdlogo";

    private final @NonNull Bot bot;

    public DVDLogoExtension(@NonNull Bot bot, @NonNull Overlay overlay) {
        super(NAME,overlay);
        this.bot = bot;
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