package perobobbot.puckwar.game;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.lang.fp.Consumer1;
import perobobbot.rendering.Renderable;
import perobobbot.rendering.Renderer;
import perobobbot.rendering.Size;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@RequiredArgsConstructor
public class PuckWarGame implements Renderable {

    private final @NonNull Size overlaySize;

    private final int puckSize;

    private final @NonNull Duration roundDuration;

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
                roundDuration,
                Instant.now(),
                overlaySize,
                puckSize
        );
    }

    public void updateGame(double dt) {
        final PuckWarRound puckWarRound = this.currentRound;
        if (puckWarRound == null) {
            return;
        }
        if (puckWarRound.isRoundOver()) {
            if (!stopAtNextRound) {
                launchANewRound();
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

}
