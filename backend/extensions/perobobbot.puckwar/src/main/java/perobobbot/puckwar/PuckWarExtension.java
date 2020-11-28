package perobobbot.puckwar;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.extension.ExtensionBase;
import perobobbot.lang.SubscriptionHolder;
import perobobbot.overlay.api.Overlay;
import perobobbot.puckwar.game.GameOptions;
import perobobbot.puckwar.game.PuckWarGame;
import perobobbot.puckwar.game.PuckWarRound;

import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
public class PuckWarExtension extends ExtensionBase {

    public static final String EXTENSION_NAME = "puck-war";

    private final @NonNull Overlay overlay;

    private PuckWarGame puckWarGame = null;

    private final SubscriptionHolder subscriptionHolder = new SubscriptionHolder();

    @Override
    public @NonNull String getName() {
        return EXTENSION_NAME;
    }

    @Override
    public boolean isAutoStart() {
        return true;
    }

    @Override
    protected void onEnabled() {
        super.onEnabled();
        this.startGame(new GameOptions(20,Duration.ofSeconds(3)));
    }

    @Override
    protected void onDisabled() {
        super.onDisabled();
        subscriptionHolder.unsubscribe();
    }

    @Synchronized
    public void startGame(@NonNull GameOptions gameOptions) {
        if (!isEnabled() && puckWarGame!=null && puckWarGame.isRunning()) {
            return;
        }
        puckWarGame = new PuckWarGame(overlay.getOverlaySize(), gameOptions);
        puckWarGame.start();
        subscriptionHolder.replaceWith(() -> overlay.addClient(new PuckWarOverlay(puckWarGame)));
    }

    @Synchronized
    public void stopGame() {
        subscriptionHolder.unsubscribe();
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
