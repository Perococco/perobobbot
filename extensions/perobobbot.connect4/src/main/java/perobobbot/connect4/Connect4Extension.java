package perobobbot.connect4;

import lombok.NonNull;
import perobobbot.extension.OverlayExtension;
import perobobbot.overlay.api.Overlay;

public class Connect4Extension extends OverlayExtension {

    public static final String NAME = "Connect 4";


    public Connect4Extension(@NonNull Overlay overlay) {
        super(NAME, overlay);
    }

    public void start() {
        final Connect4Overlay overlay = new Connect4Overlay();
        attachClient(overlay);
    }

    public void stop() {
        detachClient();
    }


}
