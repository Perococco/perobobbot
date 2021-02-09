package perobobbot.dungeon;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import perobobbot.extension.OverlayExtension;
import perobobbot.overlay.api.Overlay;
import perobobbot.rendering.Region;
import perococco.gen.generator.DungeonGenerator;
import perococco.jdgen.api.JDGenConfiguration;
import perococco.jdgen.api.Map;

import java.util.Optional;

@Log4j2
public class DungeonExtension extends OverlayExtension {

    public static final String NAME = "Dungeon";

    public final @NonNull DungeonGenerator generator;

    public DungeonExtension(@NonNull Overlay overlay) {
        super(NAME, overlay);
        this.generator = DungeonGenerator.create();
    }


    public void start(@NonNull JDGenConfiguration configuration) {
        generateMap(configuration)
                .ifPresentOrElse(this::attachOverlayClient, this::warnForMapGenerationFailure);
    }

    private void attachOverlayClient(@NonNull Map map) {
        final var overlay = new DungeonOverlay(map,computeSmallRegion());
        attachClient(overlay);
    }

    private void warnForMapGenerationFailure() {
        LOG.warn("Map generation failed. Dungeon game could not be started");
    }


    public void stop() {
        detachClient();
    }

    private @NonNull Optional<Map> generateMap(JDGenConfiguration configuration) {
        for (int i = 0; i < 10 && !Thread.currentThread().isInterrupted(); i++) {
            try {
                var map = generator.generate(configuration);
                return Optional.of(map);
            } catch (RuntimeException ignored) {
                ignored.printStackTrace();
            }
        }
        return Optional.empty();
    }

    public Region computeSmallRegion() {
        var size = getOverlaySize();
        int w = 318*size.getWidth()/1600;
        int h = 660*size.getHeight()/900;

        return new Region(size.getWidth()-w, size.getHeight()-h, w, h);
    }

    public Region computeBigRegion() {
        var size = getOverlaySize();
        int w = size.getWidth()/3;
        int h = size.getHeight();

        return new Region((size.getWidth()-w)*0.5, 0, w, h);
    }

}
