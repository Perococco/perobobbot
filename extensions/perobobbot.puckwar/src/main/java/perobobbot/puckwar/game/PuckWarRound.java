package perobobbot.puckwar.game;

import lombok.NonNull;
import perobobbot.lang.fp.UnaryOperator1;
import perobobbot.physics.CircleBounding;
import perobobbot.physics.GravityEffect;
import perobobbot.physics.ImmutableVector2D;
import perobobbot.physics.Universe;
import perobobbot.rendering.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Predicate;

public class PuckWarRound implements Renderable {

    public static final Color BACKGROUND_COLOR = new Color(0, 0, 0, 169);
    public static final int BACKGROUND_MARGIN = 20;

    public static final double WINNER_DISPLAY_DURATION_IN_SEC = 10;

    public static @NonNull PuckWarRound create(@NonNull Duration duration,
                                               @NonNull Size overlaySize,
                                               int puckSize) {
        final var initialPosition = ImmutableVector2D.cartesian(overlaySize.getWidth(), overlaySize.getHeight());

        final int targetRadius = (int) Math.round(overlaySize.getMinLength() / 6.);
        final var targetImage = TargetImage.create(2*targetRadius);
        final BufferedImage blackHoleImage = Images.BLACK_HOLE.getImage();

        final var positions = PositionsPicker.pick(overlaySize, targetRadius*2);

        final Sprite target;
        {
            final var targetPosition = positions.getTargetPosition();
            target = new Sprite("Target", targetImage);
            target.getPosition().setTo(targetPosition);
            target.setFixed(true);
            target.setBoundingBox(new CircleBounding(target, targetRadius));
            target.setAccelerationsModifier(a -> a.getGravitationAcceleration().nullify());
        }

        final Sprite blackHole;
        {
            blackHole = new Sprite("Black Hole", blackHoleImage);
            blackHole.getPosition().setTo(positions.getBlackHolePosition());
            blackHole.setFixed(true);
            blackHole.setMass(1e17);
            blackHole.setAngularSpeed(-2 * Math.PI / 30);
            blackHole.setGravityEffect(GravityEffect.ATTRACTOR);
        }

        return new PuckWarRound(
                duration,
                puckSize,
                target,
                blackHole,
                initialPosition,
                v -> v.scale(-5),
                overlaySize);
    }

    /**
     * The default size of the pucks
     */
    private final int puckSize;

    private final @NonNull Sprite help;

    private final @NonNull Sprite target;

    private final @NonNull Sprite blackHole;

    /**
     * The initial position of all pucks
     */
    private final @NonNull ImmutableVector2D initialPosition;

    /**
     * A modifier of the velocity so that the positive velocities are directed to the center of the overlay
     */
    private final @NonNull UnaryOperator1<ImmutableVector2D> velocityModifier;

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
    private final @NonNull Universe universe = Universe.create();

    private final @NonNull HighScoreTable highScoreTable = HighScoreTable.higherIsBetter(5);

    private double remainingTime = 0.0;

    private final double invTargetRadius;


    public PuckWarRound(@NonNull Duration duration,
                        int puckSize,
                        @NonNull Sprite target,
                        @NonNull Sprite blackHole,
                        @NonNull ImmutableVector2D initialPosition,
                        @NonNull UnaryOperator1<ImmutableVector2D> velocityModifier,
                        @NonNull Size overlaySize) {
        this.puckSize = puckSize;
        this.target = target;
        this.blackHole = blackHole;
        this.initialPosition = initialPosition;
        this.velocityModifier = velocityModifier;
        this.isOutsideGameRegion = new OutsiderPredicate(overlaySize);
        this.remainingTime = duration.getSeconds();

        this.invTargetRadius = 2./target.getWidth();

        this.help = new Sprite("Help", StartPointImage.create(overlaySize.getMinLength()/4));
        this.help.setFixed(true);
        this.help.getPosition().setTo(initialPosition.subtract(help.getWidth()*0.5,help.getHeight()*0.5));

        this.universe.addEntity(help);
        this.universe.addEntity(target);
        this.universe.addEntity(blackHole);
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
        this.drawHelp(renderer);
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

    private void drawHelp(Renderer renderer) {
        this.help.drawWith(renderer);
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
             .addString(Messager.formWinnerMessage(score), HAlignment.MIDDLE)
             .build()
             .draw(size.getWidth() / 2.0, size.getHeight() / 2.0, HAlignment.MIDDLE, VAlignment.MIDDLE);
        });
    }


    private void drawRemainingTime(@NonNull Renderer renderer) {
        renderer.withPrivateContext(r -> {
            final Size size = r.getDrawingSize();
            final String remainingTimeText = Messager.formRemainingTimeMessage(remainingTime);
            r.setColor(Color.WHITE);
            r.setFontSize(28);
            r.drawString(remainingTimeText, size.getWidth() * 0.5, 10, HAlignment.MIDDLE, VAlignment.TOP);
        });
    }

    public void dispose() {
        pucks.clear();
        pendingThrows.clear();
    }

    public boolean isRoundOver() {
        final double extraTimeAfterGame = highScoreTable.isEmpty()?0:WINNER_DISPLAY_DURATION_IN_SEC;
        return (remainingTime + extraTimeAfterGame) <= 0;
    }


    /**
     * Update the position of the pucks in the game
     *
     * @param dt the delta in time since the last update
     */
    public void updateRound(double dt) {
        this.updateRemainingTime(dt);
        this.universe.update(dt);
        if (isGamePhaseIsOver()) {
            pucks.forEach(p -> p.setFixed(true));
            this.updateHighScoreTable(true);
            return;
        }
        this.removeOutsiders();
        this.pucks.forEach(Puck::updateBending);
        this.addPendingThrowToPuckList();
        this.updateHighScoreTable(false);
    }

    public boolean isGamePhaseIsOver() {
        return remainingTime <= 0;
    }

    private void updateRemainingTime(double dt) {
        this.remainingTime -= dt;
    }

    private void removeOutsiders() {
        this.pucks.removeIf(isOutsideGameRegion);
    }

    private void addPendingThrowToPuckList() {
        final List<Throw> pendingThrows = drainPendingThrows();
        if (!isGamePhaseIsOver()) {
            pendingThrows.stream()
                         .map(thrw -> thrw.createPuck(initialPosition, puckSize))
                         .forEach(p -> {
                             pucks.add(p);
                             universe.addEntity(p);
                         });
        }
    }

    private @NonNull List<Throw> drainPendingThrows() {
        if (pendingThrows.isEmpty()) {
            return List.of();
        }
        final List<Throw> retrievedPuckThrows = new ArrayList<>(pendingThrows.size() + 10);
        pendingThrows.drainTo(retrievedPuckThrows);
        return retrievedPuckThrows;
    }

    private void updateHighScoreTable(boolean done) {
        highScoreTable.fillTable(() -> pucks.stream()
                                            .map(this::createScoreFromPuck)
                                            .filter(s -> s.getScore()>0));
    }

    private @NonNull Score createScoreFromPuck(@NonNull Puck puck) {
        final double distance = target.getPosition().normOfDifference(puck.getPosition());
        final double score = Math.max(0,1000.*(1-distance*invTargetRadius));
        final double bendingFactor = 1+puck.getBending();

        return new Score(puck.getThrower(), puck.getThrowInstant(), score*bendingFactor);
    }


}
