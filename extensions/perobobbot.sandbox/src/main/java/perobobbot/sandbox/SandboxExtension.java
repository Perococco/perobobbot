package perobobbot.sandbox;

import lombok.NonNull;
import perobobbot.extension.OverlayExtension;
import perobobbot.overlay.api.Overlay;

public class SandboxExtension extends OverlayExtension {

    public static final String NAME = "Sandbox";

    public SandboxExtension(@NonNull Overlay overlay) {
        super(NAME, overlay);
    }

    public void start() {
        this.attachClient(new SandboxOverlay());
    }

    public void stop() {
        this.detachClient();
    }
}
