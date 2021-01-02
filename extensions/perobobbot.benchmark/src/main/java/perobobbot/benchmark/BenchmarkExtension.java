package perobobbot.benchmark;

import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.benchmark.spring.BenchmarkExtensionFactory;
import perobobbot.extension.ExtensionBase;
import perobobbot.extension.OverlayExtension;
import perobobbot.lang.Bot;
import perobobbot.lang.SubscriptionHolder;
import perobobbot.overlay.api.Overlay;

public class BenchmarkExtension extends OverlayExtension {

    private BenchmarkOverlay benchmarkOverlay = null;

    public BenchmarkExtension(@NonNull Overlay overlay) {
        super(BenchmarkExtensionFactory.NAME,overlay);
    }

    @Synchronized
    public void start(int nbPuck, int radius) {
        if (!this.isEnabled() || benchmarkOverlay != null) {
            return;
        }
        this.benchmarkOverlay = BenchmarkOverlay.create(nbPuck, radius, this.getOverlaySize());
        this.attachClient(this.benchmarkOverlay);
    }

    @Synchronized
    public void stop() {
        this.detachClient();
        this.benchmarkOverlay = null;
    }

}
