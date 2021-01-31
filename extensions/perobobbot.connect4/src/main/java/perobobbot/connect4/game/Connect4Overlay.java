package perobobbot.connect4.game;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.connect4.GridPosition;
import perobobbot.connect4.Team;
import perobobbot.lang.Identity;
import perobobbot.lang.MathTool;
import perobobbot.lang.Subscription;
import perobobbot.overlay.api.Overlay;
import perobobbot.overlay.api.OverlayClient;
import perobobbot.overlay.api.OverlayIteration;
import perobobbot.rendering.*;
import perobobbot.rendering.histogram.Histogram;
import perobobbot.timeline.EasingType;
import perobobbot.timeline.Property;

import java.awt.*;
import java.time.Duration;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class Connect4Overlay implements OverlayClient, Connect4OverlayController {

    public static final float COLUMN_NUMBER_FONT_SIZE = 0.8f;

    private final @NonNull Connect4Grid connect4Grid;
    private final @NonNull Region region;
    private final @NonNull String[] columnNumbers;


    private final Identity<OverlayState> identity = Identity.create(OverlayState.initial());

    private double scale;

    private Region histogramRegion;
    private Region timerRegion;
    private Region gridRegion;

    private Histogram histogram;
    private Property winnerProperty;

    private Stroke lineStroke;
    private double time;

    private float numberFontSize;

    public Connect4Overlay(@NonNull Connect4Grid connect4Grid, @NonNull Region region) {
        this.connect4Grid = connect4Grid;
        this.region = region;
        this.columnNumbers = IntStream.range(0,connect4Grid.getNumberOfColumns())
                                      .map(i -> i+1)
                                      .mapToObj(Integer::toString)
                                      .toArray(String[]::new);
    }

    @Override
    public void initialize(@NonNull Overlay overlay) {
        final var imgWidth = region.getWidth();
        scale = imgWidth / connect4Grid.getGridImage().getWidth();

        final var imgHeight = scale * connect4Grid.getGridImage().getHeight();
        final var timerHeight = 5;
        final var histogramHeight = Math.min(100, region.getHeight() - imgHeight - timerHeight);
        final var offset = region.getHeight() - histogramHeight - imgHeight - timerHeight;

        lineStroke = new BasicStroke(MathTool.roundedToInt(5/scale));
        histogramRegion = new Region(0, offset, imgWidth, histogramHeight);
        timerRegion = new Region(0, histogramHeight, imgWidth, timerHeight);
        gridRegion = new Region(0, timerHeight, imgWidth, imgHeight);

        winnerProperty = overlay.createProperty().setEasing(EasingType.EASE_OUT_EXPO,Duration.ofMillis(250));
        histogram = new Histogram(connect4Grid.getNumberOfColumns(), overlay);
        identity.mutate(o -> o.withMargin(connect4Grid.getMargin() * scale).withSpacing(4 * scale));

        numberFontSize = (float)(COLUMN_NUMBER_FONT_SIZE/scale*connect4Grid.getPositionRadius());

        time = 0;
    }

    @Override
    public void render(@NonNull OverlayIteration iteration) {
        this.time += iteration.getDeltaTime();
        final OverlayState currentState = this.identity.getState();
        connect4Grid.update(iteration.getDeltaTime());

        iteration.getRenderer().withPrivateContext(r -> {

            r.translate(region.getX(), region.getY());
            r.translate(histogramRegion.getX(), histogramRegion.getY());
            new HistogramDrawer(r,currentState,histogramRegion.getSize()).draw();

            r.translate(timerRegion.getX(), timerRegion.getY());
            new TimerDrawer(r,currentState).draw();
            
            r.translate(gridRegion.getX(), gridRegion.getY());
            r.scale(scale);
            new ColumnNumberDrawer(r).draw();
            new GridDrawer(r).draw();
            new WinnerDrawer(r,currentState).draw();;

        });
    }

    @Override
    public Subscription setPollStarted(@NonNull Team team, @NonNull Duration pollDuration) {
        identity.mutate(s -> s.withPollStarted(team, time, pollDuration));
        return this::setPollDone;
    }

    private void setPollDone() {
        this.histogram.clear();
        this.identity.mutate(OverlayState::withPollEnded);
    }

    @Override
    public void setWinner(@NonNull Connected4 w) {
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
        winnerProperty.forceSet(0);
        identity.mutate(OverlayState::resetForNewGame);
    }


    @RequiredArgsConstructor
    private class HistogramDrawer {
        private final @NonNull Renderer renderer;
        private final @NonNull OverlayState state;
        private final @NonNull Size size;

        public void draw() {
            final var style = state.getHistogramStyle().orElse(null);
            if (style == null) {
                return;
            }
            histogram.setStyle(style);
            histogram.render(renderer, size);
        }
    }
    
    @RequiredArgsConstructor
    private class TimerDrawer {
        private final @NonNull Renderer renderer;
        private final @NonNull OverlayState state;
        
        public void draw() {
            final var timeInfo = state.getTimerInfo().orElse(null);
            if (timeInfo == null) {
                return;
            }
            final var factor = Math.max(0, timeInfo.getEndingTime() - time) * timeInfo.getInvDuration();
            renderer.setColor(timeInfo.getTeam().getColor());
            renderer.fillRect(state.getMargin() / 2, 0, timerRegion.getWidth() * factor, timerRegion.getHeight());
        }

    }

    @RequiredArgsConstructor
    private class ColumnNumberDrawer {

        private final @NonNull Renderer renderer;

        public void draw() {
            renderer.setTextAntialiasing(true);
            renderer.setColor(Color.WHITE);
            renderer.setFontSize(numberFontSize);

            for (int columnIndex = 0; columnIndex < columnNumbers.length; columnIndex++) {
                final var gridPosition = new GridPosition(connect4Grid.getNumberOfRows()-1,columnIndex);
                final var imagePosition = connect4Grid.computePositionOnImage(gridPosition);

                renderer.drawString(columnNumbers[columnIndex],imagePosition,HAlignment.MIDDLE, VAlignment.MIDDLE);
            }
        }
    }

    @RequiredArgsConstructor
    private class WinnerDrawer {

        private final @NonNull Renderer renderer;
        private final @NonNull OverlayState state;

        public void draw() {
            final var connected4 = state.getWinner().orElse(null);
            if (connected4 == null) {
                return;
            }
            final var start = connect4Grid.computePositionOnImage(connected4.getStart());
            final var delta = connect4Grid.computePositionOnImage(connected4.getEnd()).subtract(start);
            final var end = start.duplicate().addScaled(delta,winnerProperty.get());

            renderer.setStroke(lineStroke);//new BasicStroke(MathTool.roundedToInt(5/scale)));
            renderer.setPaint(Color.BLACK);
            renderer.drawLine(start, end);
        }
    }


    @RequiredArgsConstructor
    private class GridDrawer {

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
