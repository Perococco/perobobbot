package perobobbot.dungeon;

import lombok.NonNull;
import perobobbot.extension.OverlayExtension;
import perobobbot.overlay.api.Overlay;
import perococco.gen.generator.Generator;
import perococco.jdgen.api.JDGenConfiguration;

public class DungeonExtension extends OverlayExtension {

    public static final String NAME = "Dungeon";

    public final @NonNull Generator generator;

    public DungeonExtension(@NonNull Overlay overlay) {
        super(NAME, overlay);
        this.generator = Generator.create();
    }


    public void start(@NonNull JDGenConfiguration configuration) {
        System.out.println("DUNGEON START");
        generator.generate(configuration)
                 .whenComplete((r,t) -> {
                     if (t != null) {
                         t.printStackTrace();
                     } else {
                         System.out.println("Generated map "+r);
                     }
                 });
    }

    public void stop() {
        System.out.println("DUNGEON STOP");
    }
}
