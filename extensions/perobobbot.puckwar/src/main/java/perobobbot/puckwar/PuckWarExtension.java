package perobobbot.puckwar;

import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.command.CommandBundleLifeCycle;
import perobobbot.extension.ExtensionBase;
import perobobbot.extension.OverlayExtension;
import perobobbot.lang.Bot;
import perobobbot.lang.SubscriptionHolder;
import perobobbot.lang.fp.Function1;
import perobobbot.overlay.api.Overlay;
import perobobbot.puckwar.game.GameOptions;
import perobobbot.puckwar.game.PuckWarGame;

import java.util.Optional;

public class PuckWarExtension extends OverlayExtension {

    public static final String NAME = "puck-war";

    private PuckWarGame puckWarGame = null;

    public PuckWarExtension(@NonNull Bot bot, @NonNull Overlay overlay) {
        super(NAME,overlay);
    }

    @Synchronized
    public void startGame(@NonNull GameOptions gameOptions) {
        if (!isEnabled() && puckWarGame!=null && puckWarGame.isRunning()) {
            return;
        }
        puckWarGame = new PuckWarGame(this.getOverlaySize(), gameOptions);
        puckWarGame.start();
        this.attachClient(new PuckWarOverlay(puckWarGame));
    }

    @Synchronized
    public void stopGame() {
        this.detachClient();
        puckWarGame.stop();
        puckWarGame = null;
    }

    @Synchronized
    public void requestStop() {
        getCurrentGame().ifPresent(PuckWarGame::requestStop);
    }

    @Synchronized
    public @NonNull Optional<PuckWarGame> getCurrentGame() {
        return Optional.ofNullable(puckWarGame);
    }


}
