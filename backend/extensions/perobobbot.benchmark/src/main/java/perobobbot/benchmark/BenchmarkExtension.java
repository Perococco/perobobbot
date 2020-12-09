package perobobbot.benchmark;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.extension.ExtensionBase;
import perobobbot.lang.SubscriptionHolder;
import perobobbot.overlay.api.Overlay;

public class BenchmarkExtension extends ExtensionBase {

    private final @NonNull Overlay overlay;

    private final SubscriptionHolder overlaySubscription = new SubscriptionHolder();

    private final @NonNull String userId;

    private BenchmarkOverlay benchmarkOverlay = null;

    public BenchmarkExtension(@NonNull String userId, @NonNull Overlay overlay) {
        super(BenchmarkExtensionFactory.NAME);
        this.userId = userId;
        this.overlay = overlay;
    }

    @Synchronized
    public void start(int nbPuck, int radius) {
        if (!this.isEnabled() || benchmarkOverlay != null) {
            return;
        }
        this.benchmarkOverlay = BenchmarkOverlay.create(nbPuck, radius, overlay.getOverlaySize());
        this.overlaySubscription.replaceWith(() -> overlay.addClient(this.benchmarkOverlay));
    }

    @Synchronized
    public void stop() {
        this.overlaySubscription.unsubscribe();
        this.benchmarkOverlay = null;
    }

    @Override
    public boolean isAutoStart() {
        return true;
    }
}
