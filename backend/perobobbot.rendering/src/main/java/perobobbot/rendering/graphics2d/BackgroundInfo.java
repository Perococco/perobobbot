package perobobbot.rendering.graphics2d;

import lombok.*;

import java.awt.*;
import java.util.Optional;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BackgroundInfo {

    public static @NonNull BackgroundInfo withoutColor(int margin) {
        return new BackgroundInfo(null,margin);
    }

    public static @NonNull BackgroundInfo withColor(@NonNull Color color, int margin) {
        return new BackgroundInfo(color,margin);
    }

    @Getter(AccessLevel.NONE)
    Color color;

    int margin;

    public @NonNull Optional<Color> getColor() {
        return Optional.ofNullable(color);
    }

    public @NonNull BackgroundInfo withColor(@NonNull Color color) {
        return new BackgroundInfo(color,this.margin);
    }

    public @NonNull BackgroundInfo withMargin(@NonNull int margin) {
        return new BackgroundInfo(this.color,margin);
    }
}
