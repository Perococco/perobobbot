package perobobbot.benchmark;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.extension.ExtensionBase;
import perobobbot.lang.SubscriptionHolder;
import perobobbot.overlay.api.Overlay;

@RequiredArgsConstructor
public class BenchmarkExtension extends ExtensionBase {

    public static final String EXTENSION_NAME = "benchmark";

    private final @NonNull Overlay overlay;

    private final SubscriptionHolder overlaySubscription = new SubscriptionHolder();

    private BenchmarkOverlay benchmarkOverlay = null;

    @Override
    public @NonNull String getName() {
        return EXTENSION_NAME;
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
