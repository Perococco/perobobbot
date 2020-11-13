package perobobbot.puckwar.game;

import lombok.NonNull;
import perobobbot.lang.fp.UnaryOperator1;
import perobobbot.math.Vector2D;
import perobobbot.rendering.*;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Predicate;

public class PuckWarRound implements Renderable {

    public static @NonNull PuckWarRound create(@NonNull Duration duration,
                                               @NonNull Instant startingTime,
                                               @NonNull Size overlaySize,
                                               int puckSize) {
        final var initialPosition = Vector2D.of(0, overlaySize.getHeight() * 0.5);

        final Target target;
        {
            final int size = (int) Math.round(Math.min(overlaySize.getHeight(), overlaySize.getWidth()) / 3.);
            final var targetPosition = TargetPositionComputer.compute(overlaySize, initialPosition, size);
            target = new Target(targetPosition, size);
        }

        return new PuckWarRound(
                duration,
                startingTime,
                puckSize,
                target,
                initialPosition,
                v -> v.scale(5),
                new OutsiderPredicate(overlaySize));
    }

    /**
     * The duration of the round
     */
    private final @NonNull Duration duration;

    /**
     * the time at which the round has been launch
     */
    private final @NonNull Instant startingTime;

    /**
     * The default size of the pucks
     */
    private final int puckSize;

    private final @NonNull Target target;

    /**
     * The initial position of all pucks
     */
    private final @NonNull Vector2D initialPosition;

    /**
     * A modifier of the velocity so that the positive velocities are directed to the center of the overlay
     */
    private final @NonNull UnaryOperator1<Vector2D> velocityModifier;

    /**
     * Predicate to test if a puck is outside of the drawing region
     */
    private final @NonNull Predicate<Puck> isOutsideGameRegion;

    /**
     * The throw that are not included in the game yet
     */
    private final @NonNull BlockingDeque<Throw> pendingThrows = new LinkedBlockingDeque<>();

    /**
     * The list of active pucks in the game
     */
    private final @NonNull List<Puck> pucks = new ArrayList<>(256);

    private final @NonNull HighScoreTable highScoreTable = HighScoreTable.lowerIsBetter(5);

    private double remainingTime = 0.0;


    public PuckWarRound(@NonNull Duration duration,
                        @NonNull Instant startingTime,
                        int puckSize,
                        @NonNull Target target,
                        @NonNull Vector2D initialPosition,
                        @NonNull UnaryOperator1<Vector2D> velocityModifier,
                        @NonNull Predicate<Puck> isOutsideGameRegion) {
        this.duration = duration;
        this.startingTime = startingTime;
        this.puckSize = puckSize;
        this.target = target;
        this.initialPosition = initialPosition;
        this.velocityModifier = velocityModifier;
        this.isOutsideGameRegion = isOutsideGameRegion;
        this.remainingTime = duration.getSeconds();
    }

    /**
     * add a throw that will be added at the next rendering frame
     *
     * @param puckThrow the information about the throw
     */
    public void addThrow(@NonNull Throw puckThrow) {
        this.pendingThrows.add(puckThrow.modifyVelocity(velocityModifier));
    }

    public @NonNull Optional<Score> getBestScore() {
        return highScoreTable.getBestScore();
    }

    @Override
    public void drawWith(@NonNull Renderer renderer) {
        this.displayRemainingTime(renderer);
        highScoreTable.drawWith(renderer);
        target.drawWith(renderer);
        pucks.forEach(p -> p.drawWith(renderer));
    }

    private void displayRemainingTime(@NonNull Renderer renderer) {
        renderer.withPrivateContext(r -> {
            final Size size = r.getDrawingSize();
            final String remainingTimeText = String.format("End of round in %4.1f sec.",remainingTime);
            r.setFontSize(28);
            r.drawString(remainingTimeText,size.getWidth()/2,10, HAlignment.MIDDLE, VAlignment.TOP);
        });
    }

    public void dispose() {
        pucks.clear();
        pendingThrows.clear();
    }

    /**
     * Update the position of the pucks in the game
     *
     * @param dt the delta in time since the last update
     */
    public void updateRound(double dt) {
        if (isRoundOver()) {
            return;
        }
        this.updateRemainingTime(dt);
        this.updatePuckPositions(dt);
        this.removeOutsiders();
        this.addPendingThrowToPuckList();
        this.updateHighScoreTable();
    }

    public boolean isRoundOver() {
        return remainingTime<=0;
    }

    private void updateRemainingTime(double dt) {
        this.remainingTime -= dt;
    }


    private void updatePuckPositions(double dt) {
        this.pucks.forEach(p -> p.update(dt));
    }

    private void removeOutsiders() {
        this.pucks.removeIf(isOutsideGameRegion);
    }

    private void addPendingThrowToPuckList() {
        drainPendingThrows()
                .stream()
                .map(thrw -> thrw.createPuck(initialPosition, puckSize))
                .forEach(pucks::add);
    }

    private @NonNull List<Throw> drainPendingThrows() {
        if (pendingThrows.isEmpty()) {
            return List.of();
        }
        final List<Throw> retrievedPuckThrows = new ArrayList<>(pendingThrows.size() + 10);
        pendingThrows.drainTo(retrievedPuckThrows);
        return retrievedPuckThrows;
    }

    private void updateHighScoreTable() {
        highScoreTable.fillTable(pucks, this::createScoreFromPuck);
    }

    private @NonNull Score createScoreFromPuck(@NonNull Puck puck) {
        final double distance = target.getPosition().distanceTo(puck.getPosition());
        return new Score(puck.getThrower(), puck.getThrowInstant(), distance);
    }



}
