package perobobbot.dvdlogo;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.common.lang.SubscriptionHolder;
import perobobbot.overlay.Overlay;
import perobobbot.program.core.ProgramBase;

@RequiredArgsConstructor
public class DVDLogoProgram extends ProgramBase {

    @Getter
    private final @NonNull String name;

    private final @NonNull Overlay overlay;

    private final SubscriptionHolder overlaySubscription = new SubscriptionHolder();

    @Override
    protected void onDisabled() {
        super.onDisabled();
        stopOverlay();
    }

    @Synchronized
    public void startOverlay() {
        if (overlaySubscription.hasSubscription()) {
            return;
        }
        this.overlaySubscription.replaceWith(() -> overlay.addClient(new DVDLogoOverlay()));
    }

    @Synchronized
    public void stopOverlay() {
        overlaySubscription.unsubscribe();
    }
}
