package perobobbot.benchmark;

import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.benchmark.spring.BenchmarkExtensionFactory;
import perobobbot.extension.OverlayExtension;
import perobobbot.overlay.api.Overlay;

public class BenchmarkExtension extends OverlayExtension {

    public BenchmarkExtension(@NonNull Overlay overlay) {
        super(BenchmarkExtensionFactory.NAME,overlay);
    }

    @Synchronized
    public void start(int nbPuck, int radius) {
        if (!this.isEnabled() || this.isClientAttached()) {
            return;
        }
        var overlayClient = BenchmarkOverlay.create(nbPuck, radius, this.getOverlaySize());
        this.attachClient(overlayClient);
    }

    @Synchronized
    public void stop() {
        this.detachClient();
    }

}
