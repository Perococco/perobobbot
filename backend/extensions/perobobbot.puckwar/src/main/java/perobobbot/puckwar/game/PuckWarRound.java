package perobobbot.puckwar.game;

import lombok.NonNull;
import perobobbot.lang.fp.UnaryOperator1;
import perobobbot.math.Vector2D;
import perobobbot.rendering.*;

import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Predicate;

public class PuckWarRound implements Renderable {

    public static final Color BACKGROUND_COLOR = new Color(255, 255, 255, 92);
    public static final int BACKGROUND_MARGIN = 20;

    public static final double WINNER_DISPLAY_DURATION_IN_SEC = 10;

    public static @NonNull PuckWarRound create(@NonNull Duration duration,
                                               @NonNull Instant startingTime,
                                               @NonNull Size overlaySize,
                                               int puckSize) {
        final var initialPosition = Vector2D.of(0, overlaySize.getHeight() * 0.5);

        final var positionProvider = new RandomPositionProvider(overlaySize);

        final Target target;
        {
            final int size = (int) Math.round(Math.min(overlaySize.getHeight(), overlaySize.getWidth()) / 3.);
            final var targetPosition = positionProvider.compute(size/2, 0.66, 1.0);
            target = new Target(targetPosition, size);
        }
        final BlackHole blackHole;
        {
            final int size = BlackHole.getImageSize();
            final var blackHolePosition = positionProvider.compute(size/2,0.25,0.5);
            blackHole = BlackHole.create(blackHolePosition);
        }

        return new PuckWarRound(
                duration,
                startingTime,
                puckSize,
                target,
                blackHole,
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


    private final @NonNull BlackHole blackHole;


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
                        @NonNull BlackHole blackHole,
                        @NonNull Vector2D initialPosition,
                        @NonNull UnaryOperator1<Vector2D> velocityModifier,
                        @NonNull Predicate<Puck> isOutsideGameRegion) {
        this.duration = duration;
        this.startingTime = startingTime;
        this.puckSize = puckSize;
        this.target = target;
        this.blackHole = blackHole;
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
        this.drawTarget(renderer);
        this.drawBlackHole(renderer);
        this.drawPucks(renderer);
        this.drawHighScoreTable(renderer);
        if (isGamePhaseIsOver()) {
            highScoreTable.getBestScore()
                          .ifPresent(score -> this.drawWinner(renderer, score));
        } else {
            this.drawRemainingTime(renderer);
        }
    }

    private void drawHighScoreTable(@NonNull Renderer renderer) {
        highScoreTable.drawWith(renderer);
    }

    private void drawTarget(@NonNull Renderer renderer) {
        target.drawWith(renderer);
    }

    private void drawBlackHole(@NonNull Renderer renderer) {
        blackHole.drawWith(renderer);
    }


    private void drawPucks(@NonNull Renderer renderer) {
        pucks.forEach(p -> p.drawWith(renderer));
    }

    private void drawWinner(@NonNull Renderer renderer, @NonNull Score score) {
        final Size size = renderer.getDrawingSize();
        renderer.withPrivateContext(r -> {
            r.blockBuilder()
             .setBackgroundColor(BACKGROUND_COLOR)
             .setBackgroundMargin(BACKGROUND_MARGIN)
             .setFontSize(48)
             .setColor(Color.WHITE)
             .addString(Messager.formWinnerMessage(score),HAlignment.MIDDLE)
             .build()
             .draw(size.getWidth()/2, size.getHeight()/2,HAlignment.MIDDLE, VAlignment.MIDDLE);
        });
    }


    private void drawRemainingTime(@NonNull Renderer renderer) {
        renderer.withPrivateContext(r -> {
            final Size size = r.getDrawingSize();
            final String remainingTimeText = Messager.formRemainingTimeMessage(remainingTime);
            r.setColor(Color.WHITE);
            r.setFontSize(28);
            r.drawString(remainingTimeText, size.getWidth()*0.5, 10, HAlignment.MIDDLE, VAlignment.TOP);
        });
    }

    public void dispose() {
        pucks.clear();
        pendingThrows.clear();
    }

    public boolean isRoundOver() {
        return (remainingTime + WINNER_DISPLAY_DURATION_IN_SEC) <= 0;
    }


    /**
     * Update the position of the pucks in the game
     *
     * @param dt the delta in time since the last update
     */
    public void updateRound(double dt) {
        this.updateRemainingTime(dt);
        this.blackHole.update(dt);
        if (isGamePhaseIsOver()) {
            return;
        }
        this.updatePuckPositions(dt);
        this.removeOutsiders();
        this.addPendingThrowToPuckList();
        this.updateHighScoreTable();
    }

    public boolean isGamePhaseIsOver() {
        return remainingTime <= 0;
    }

    private void updateRemainingTime(double dt) {
        this.remainingTime -= dt;
    }

    private void updatePuckPositions(double dt) {
        this.pucks.forEach(p -> p.update(dt,blackHole,target));
    }

    private void removeOutsiders() {
        this.pucks.removeIf(isOutsideGameRegion);
    }

    private void addPendingThrowToPuckList() {
        drainPendingThrows()
                .stream()
                .map(thrw -> thrw.createPuck(initialPosition, puckSize))
                .map(this::prepareCheatingPuck)
                .forEach(pucks::add);
    }

    private @NonNull Puck prepareCheatingPuck(@NonNull Puck puck) {
        if (puck.isACheater()) {
            puck.getPosition().setTo(target.getPosition());
            puck.clearVelocity();
        }
        return puck;
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
