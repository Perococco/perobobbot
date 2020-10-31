package perobobbot.program.dvdlogo;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.access.core.Policy;
import perobobbot.common.lang.SubscriptionHolder;
import perobobbot.common.messaging.ChatController;
import perobobbot.overlay.Overlay;
import perobobbot.program.core.Program;

@RequiredArgsConstructor
public class DVDLogoProgram implements Program {

    @Getter
    private final @NonNull String name;

    private final @NonNull Overlay overlay;

    private final @NonNull ChatController chatController;

    private final @NonNull Policy policy;

    private final SubscriptionHolder commandSubscription = new SubscriptionHolder();

    private final SubscriptionHolder overlaySubscription = new SubscriptionHolder();

    @Override
    public void enable() {
        commandSubscription.replaceWith(
                chatController.addCommand("dl-start", policy.createAccessPoint(ctx -> this.startOverlay())),
                chatController.addCommand("dl-stop", policy.createAccessPoint(ctx -> this.stopOverlay()))
        );
    }

    @Override
    public void disable() {
        commandSubscription.unsubscribe();
        stopOverlay();
    }

    @Override
    public boolean isEnabled() {
        return commandSubscription.hasSubscription();
    }

    @Synchronized
    public void startOverlay() {
        if (overlaySubscription.hasSubscription()) {
            return;
        }
        this.overlaySubscription.replaceWith(overlay.addClient(new DVDLogoOverlay()));
    }

    @Synchronized
    public void stopOverlay() {
        overlaySubscription.unsubscribe();
    }
}
