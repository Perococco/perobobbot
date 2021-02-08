package perobobbot.dungeon;

import lombok.NonNull;
import perobobbot.extension.OverlayExtension;
import perobobbot.overlay.api.Overlay;

public class DungeonExtension extends OverlayExtension {

    public static final String NAME = "Dungeon";

    public DungeonExtension(@NonNull Overlay overlay) {
        super(NAME, overlay);
    }


    public void start() {
        System.out.println("DUNGEON START");
    }

    public void stop() {
        System.out.println("DUNGEON STOP");
    }
}
