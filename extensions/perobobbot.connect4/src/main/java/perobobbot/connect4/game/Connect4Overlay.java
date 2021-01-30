package perobobbot.connect4.game;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.connect4.Connect4Constants;
import perobobbot.connect4.GridPosition;
import perobobbot.connect4.TokenType;
import perobobbot.lang.Identity;
import perobobbot.lang.MathTool;
import perobobbot.lang.Subscription;
import perobobbot.overlay.api.Overlay;
import perobobbot.overlay.api.OverlayClient;
import perobobbot.overlay.api.OverlayIteration;
import perobobbot.rendering.HAlignment;
import perobobbot.rendering.Region;
import perobobbot.rendering.Renderer;
import perobobbot.rendering.VAlignment;
import perobobbot.rendering.histogram.Histogram;
import perobobbot.timeline.EasingType;
import perobobbot.timeline.Property;

import java.awt.*;
import java.time.Duration;

import static perobobbot.connect4.Connect4Constants.NB_COLUMNS;
import static perobobbot.connect4.Connect4Constants.NB_ROWS;

@RequiredArgsConstructor
public class Connect4Overlay implements OverlayClient, Connect4OverlayController {

    public static final float COLUMN_NUMBER_FONT_SIZE = 0.8f;

    private final @NonNull Connect4Grid connect4Grid;
    private final @NonNull Region region;


    private final Identity<OverlayState> identity = Identity.create(OverlayState.initial());

    private double scale;

    private Region histogramRegion;
    private Region timerRegion;
    private Region gridRegion;

    private Histogram histogram;

    private Property winnerProperty;

    private double time;

    @Override
    public void initialize(@NonNull Overlay overlay) {
        final var imgWidth = region.getWidth();
        this.scale = imgWidth / connect4Grid.getGridImage().getWidth();
        final var imgHeight = scale * connect4Grid.getGridImage().getHeight();
        final var lineHeight = 5;
        final var histogramHeight = Math.min(100, region.getHeight() - imgHeight - lineHeight);
        final var offset = region.getHeight() - histogramHeight - imgHeight - lineHeight;

        this.histogramRegion = new Region(0, offset, imgWidth, histogramHeight);
        this.timerRegion = new Region(0, histogramHeight, imgWidth, lineHeight);
        this.gridRegion = new Region(0, lineHeight, imgWidth, imgHeight);

        this.winnerProperty = overlay.createProperty().setEasing(EasingType.EASE_OUT_EXPO,Duration.ofMillis(250));
        this.histogram = new Histogram(connect4Grid.getNumberOfColumns(), overlay);
        this.identity.mutate(o -> o.withMargin(connect4Grid.getMargin() * scale).withSpacing(4 * scale));

        this.time = 0;
    }

    @Override
    public void render(@NonNull OverlayIteration iteration) {
        this.time += iteration.getDeltaTime();
        final OverlayState currentState = this.identity.getState();
        connect4Grid.update(iteration.getDeltaTime());

        iteration.getRenderer().withPrivateContext(r -> {

            r.translate(region.getX(), region.getY());

            r.translate(histogramRegion.getX(), histogramRegion.getY());
            currentState.getHistogramStyle().ifPresent(s -> {
                histogram.setStyle(s);
                histogram.render(r, histogramRegion.getSize());
            });

            r.translate(timerRegion.getX(), timerRegion.getY());

            currentState.getTimerInfo().ifPresent(t -> {
                final var factor = Math.max(0, t.getEndingTime() - time) * t.getInvDuration();
                r.setColor(t.getTeam().getColor());
                r.fillRect(currentState.getMargin() / 2, 0, timerRegion.getWidth() * factor, timerRegion.getHeight());
            });


            r.translate(gridRegion.getX(), gridRegion.getY());
            r.scale(scale);

            for (int columnIndex = 0; columnIndex < NB_COLUMNS; columnIndex++) {
                final var pos = connect4Grid.computePositionOnImage(new GridPosition(NB_ROWS-1, columnIndex));

                r.setTextAntialiasing(true);
                r.setColor(Color.WHITE);
                r.setFontSize((float)(COLUMN_NUMBER_FONT_SIZE/scale*connect4Grid.getPositionRadius()));
                r.drawString(String.valueOf(columnIndex+1), pos.getX(), pos.getY(), HAlignment.MIDDLE, VAlignment.MIDDLE);

            }

            new Drawing(connect4Grid, r).draw();

            currentState.getWinner().ifPresent(c -> {
                final var start = connect4Grid.computePositionOnImage(c.getStart());
                final var delta = connect4Grid.computePositionOnImage(c.getEnd()).subtract(start);
                final var end = start.duplicate().addScaled(delta,winnerProperty.get());

                r.setStoke(new BasicStroke(MathTool.roundedToInt(5/scale)));
                r.setColor(Color.BLACK);
                r.drawLine(start, end);
            });

        });
    }

    @Override
    public Subscription setPollStarted(@NonNull TokenType team, @NonNull Duration pollDuration) {
        identity.mutate(s -> s.withPollStarted(team, time, pollDuration));
        return this::setPollDone;
    }

    private void setPollDone() {
        this.histogram.clear();
        this.identity.mutate(OverlayState::withPollEnded);
    }

    @Override
    public void setWinner(@NonNull Connected4 w) {
        System.out.println("Winner is "+w.getWinningTeam());
        winnerProperty.forceSet(0);
        winnerProperty.set(1);
        identity.mutate(s -> s.withWinner(w));
    }

    @Override
    public void setDraw() {
        identity.mutate(OverlayState::withDraw);
    }

    @Override
    public void setHistogramValues(int index, int value) {
        histogram.setValue(index, value);
    }

    @Override
    public void resetForNewGame() {
        this.winnerProperty.forceSet(0);
        this.identity.mutate(OverlayState::resetForNewGame);
    }

    @RequiredArgsConstructor
    private static class Drawing {

        private final @NonNull Connect4Grid connect4Grid;

        private final @NonNull Renderer renderer;

        public void draw() {
            connect4Grid.forEachTokens(this::drawToken);
            drawGrid();
        }

        private void drawToken(Token token) {
            final var position = token.getPosition();
            renderer.setColor(token.getColor());
            renderer.fillCircle(MathTool.roundedToInt(position.getX()), MathTool.roundedToInt(position.getY()), token.getRadius());
        }

        private void drawGrid() {
            final var bufferedImage = connect4Grid.getGridImage();
            renderer.drawImage(bufferedImage, 0, 0);
        }

    }

}
