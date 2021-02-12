package perobobbot.dungeon;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import perobobbot.dungeon.game.Dungeon;
import perobobbot.dungeon.game.GameController;
import perobobbot.dungeon.game.generation.DungeonCreator;
import perobobbot.extension.OverlayExtension;
import perobobbot.overlay.api.Overlay;
import perobobbot.rendering.Region;
import perococco.jdgen.api.JDGenConfiguration;

@Log4j2
public class DungeonExtension extends OverlayExtension {

    public static final String NAME = "Dungeon";

    public DungeonExtension(@NonNull Overlay overlay) {
        super(NAME, overlay);
    }


    public void start(@NonNull JDGenConfiguration configuration) {
        DungeonCreator.create(configuration)
                      .map(GameController::new)
                      .ifPresentOrElse(this::attachOverlayClient, this::warnForMapGenerationFailure);
    }

    private void attachOverlayClient(@NonNull GameController gameController) {
        final var overlay = new DungeonOverlay(gameController,computeWholeRegion());
        attachClient(overlay);
    }

    private void warnForMapGenerationFailure() {
        LOG.warn("Map generation failed. Dungeon game could not be started");
    }


    public void stop() {
        detachClient();
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

    public Region computeWholeRegion() {
        var size = getOverlaySize();
        int w = size.getWidth();
        int h = size.getHeight();

        return new Region(0, 0, w, h);
    }

}
