package perobobbot.connect4;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import perobobbot.connect4.game.Connected4;
import perobobbot.rendering.histogram.HistogramStyle;
import perobobbot.rendering.histogram.Orientation;
import perobobbot.rendering.histogram.RoundBrighter;
import perobobbot.timeline.EasingType;

import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
@Builder(toBuilder = true)
public class OverlayState {

    public static @NonNull OverlayState initial() {
        return new OverlayState(0,0,false,null,null,null);
    }

    private final double margin;
    private final double spacing;
    private final boolean draw;
    private final Connected4 winner;
    private final HistogramStyle histogramStyle;
    private final TimerInfo timerInfo;


    public @NonNull Optional<TimerInfo> getTimerInfo() {
        return Optional.ofNullable(timerInfo);
    }

    public @NonNull Optional<Connected4> getWinner() {
        return Optional.ofNullable(winner);
    }

    public @NonNull OverlayState withMargin(double margin) {
        return toBuilder().margin(margin).build();
    }

    public @NonNull OverlayState withSpacing(double spacing) {
        return toBuilder().spacing(spacing).build();
    }


    public @NonNull Optional<HistogramStyle> getHistogramStyle() {
        return Optional.ofNullable(histogramStyle);
    }


    private @NonNull HistogramStyle createStyle(@NonNull TokenType type) {
        return HistogramStyle.builder()
                             .margin(margin)
                             .spacing(spacing)
                             .easingDuration(Duration.ofMillis(500))
                             .orientation(Orientation.BOTTOM_TO_TOP)
                             .easingType(EasingType.EASE_OUT_SINE)
                             .barDrawer(new RoundBrighter(type.getColor()).createBarDrawer())
                             .build();
    }

    public OverlayState withWinner(@NonNull Connected4 winner) {
        return toBuilder().winner(winner).draw(false).build();
    }

    public OverlayState withDraw() {
        return toBuilder().draw(true).winner(null).build();
    }

    public OverlayState resetForNewGame() {
        return toBuilder().winner(null).draw(false).histogramStyle(null).build();
    }

    public OverlayState withPollStarted(@NonNull TokenType team, double startingTime, Duration duration) {
        final var durationInSeconds = duration.toMillis()*1e-3;
        return toBuilder().timerInfo(new TimerInfo(team,startingTime+durationInSeconds,1./durationInSeconds))
                          .histogramStyle(createStyle(team))
                          .build();
    }

    public OverlayState withPollEnded() {
        return toBuilder().timerInfo(null).histogramStyle(null).build();
    }

    public double getMargin() {
        return margin;
    }

    @Value
    public static class TimerInfo {
        @NonNull TokenType team;
        double endingTime;
        double invDuration;
    }
}
