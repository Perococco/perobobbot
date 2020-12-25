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

    private final @NonNull Bot bot;

    private BenchmarkOverlay benchmarkOverlay = null;

    public BenchmarkExtension(@NonNull Bot bot, @NonNull Overlay overlay) {
        super(BenchmarkExtensionFactory.NAME,overlay);
        this.bot = bot;
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
