package perobobbot.program.dvdlogo;

import lombok.*;
import perobobbot.access.core.Policy;
import perobobbot.chat.core.ChatController;
import perobobbot.common.lang.Subscription;
import perobobbot.common.lang.SubscriptionHolder;
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
    public void start() {
        commandSubscription.replace(Subscription.join(
                chatController.addCommand("dl-start", policy.createAccessPoint(ctx -> this.startOverlay())),
                chatController.addCommand("dl-stop", policy.createAccessPoint(ctx -> this.stopOverlay()))
        ));
    }

    @Override
    public void requestStop() {
        commandSubscription.unsubscribe();
        overlaySubscription.unsubscribe();
    }

    @Override
    public boolean isRunning() {
        return commandSubscription.hasSubscription();
    }

    @Synchronized
    public void startOverlay() {
        if (overlaySubscription.hasSubscription()) {
            return;
        }
        this.overlaySubscription.replace(overlay.addClient(new DVDLogoOverlay()));
    }

    @Synchronized
    public void stopOverlay() {
        overlaySubscription.unsubscribe();
    }
}
