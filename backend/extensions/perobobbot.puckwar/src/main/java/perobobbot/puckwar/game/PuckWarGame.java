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


    @Synchronized
    public void start() {
        launchANewRound();
    }

    @Synchronized
    public void requestStop() {
        //end game at the end of next round
    }

    @Synchronized
    public void stop() {
        //force end of game even if a round is in progress
    }

    private void launchANewRound() {
        assert currentRound == null; //TODO handle end of round (display best score)
        currentRound = PuckWarRound.create(
                roundDuration,
                Instant.now(),
                overlaySize,
                puckSize
        );
    }

    public void updateGame(double dt) {
        executeWithCurrentRound(r -> r.updateRound(dt));
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
