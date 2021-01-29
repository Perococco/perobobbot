package perobobbot.rendering.histogram;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import perobobbot.rendering.Renderer;
import perobobbot.timeline.EasingType;

import java.awt.*;
import java.time.Duration;

@Value
@Builder(builderClassName = "Builder")
public class HistogramStyle {
    @NonNull Orientation orientation;
    @NonNull EasingType easingType;
    @NonNull Duration easingDuration;
    int margin;
    int spacing;
    @NonNull BarDrawer barDrawer;

    public static @NonNull Builder builder() {
        return new Builder()
                .orientation(Orientation.BOTTOM_TO_TOP)
                .easingType(EasingType.EASE_OUT_SINE)
                .easingDuration(Duration.ofSeconds(1))
                .barDrawer(SimpleBarDrawer.plain(Color.BLUE))
                .spacing(0);
    }

    public void drawBar(Renderer renderer, double barWidth, double barHeight, double valueInPercent) {
        barDrawer.drawBar(renderer, barWidth, barHeight, valueInPercent);
    }
}
