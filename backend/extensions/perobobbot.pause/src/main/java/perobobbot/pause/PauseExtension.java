package perobobbot.pause;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.extension.ExtensionBase;
import perobobbot.lang.Subscription;
import perobobbot.lang.SubscriptionHolder;
import perobobbot.lang.Todo;
import perobobbot.overlay.api.Overlay;

import java.time.Duration;

public class PauseExtension extends ExtensionBase {

    @Getter
    private final @NonNull String userId;

    private final Overlay overlay;

    private final SubscriptionHolder subscriptionHolder = new SubscriptionHolder();

    public PauseExtension(@NonNull String userId, @NonNull Overlay overlay) {
        super(PauseExtensionFactory.NAME);
        this.userId = userId;
        this.overlay = overlay;
    }

    @Override
    protected void onDisabled() {
        super.onDisabled();
        subscriptionHolder.unsubscribe();
    }

    @Override
    public boolean isAutoStart() {
        return false;
    }

    public void startPause(@NonNull Duration duration) {
        subscriptionHolder.replaceWith(()-> displayCountDown(duration));
    }

    private @NonNull Subscription displayCountDown(@NonNull Duration duration) {
        return overlay.addClient(new PauseOverlayClient(duration));
    }

    public void stopPause() {
        subscriptionHolder.unsubscribe();
    }
}
