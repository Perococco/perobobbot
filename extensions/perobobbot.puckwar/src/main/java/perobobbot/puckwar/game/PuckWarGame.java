package perobobbot.puckwar.game;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.lang.fp.Consumer1;
import perobobbot.rendering.Renderable;
import perobobbot.rendering.Renderer;
import perobobbot.rendering.ScreenSize;

import java.util.Optional;

@RequiredArgsConstructor
public class PuckWarGame implements Renderable {

    private final @NonNull ScreenSize overlaySize;

    private final @NonNull GameOptions gameOptions;

    private PuckWarRound currentRound = null;

    private boolean stopAtNextRound = false;

    @Synchronized
    public void start() {
        stopAtNextRound = false;
        launchANewRound();
    }

    @Synchronized
    public void requestStop() {
        stopAtNextRound = true;
    }

    @Synchronized
    public void stop() {
        stopAtNextRound = true;
        executeWithCurrentRound(PuckWarRound::dispose);
        currentRound = null;
    }

    private void launchANewRound() {
        currentRound = PuckWarRound.create(
                gameOptions.getRoundDuration(),
                overlaySize,
                gameOptions.getPuckSize()
        );
    }

    public void updateGame(double dt) {
        final PuckWarRound puckWarRound = this.currentRound;
        if (puckWarRound == null) {
            return;
        }
        if (puckWarRound.isRoundOver()) {
            puckWarRound.dispose();
            if (!stopAtNextRound) {
                launchANewRound();
            } else {
                this.stop();
            }
        } else {
            puckWarRound.updateRound(dt);
        }

    }

    @Override
    public void drawWith(@NonNull Renderer renderer) {
        executeWithCurrentRound(r -> r.drawWith(renderer));
    }

    public void addThrow(@NonNull Throw puckThrow) {
        executeWithCurrentRound(r -> r.addThrow(puckThrow));
    }

    private void executeWithCurrentRound(@NonNull Consumer1<? super PuckWarRound> action) {
        Optional.ofNullable(currentRound).ifPresent(action);
    }

    public boolean isRunning() {
        final PuckWarRound puckWarRound = this.currentRound;
        return puckWarRound != null && !puckWarRound.isRoundOver();
    }
}
