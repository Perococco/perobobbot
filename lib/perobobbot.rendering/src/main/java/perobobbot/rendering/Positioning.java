package perobobbot.rendering;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class Positioning {

    int x;
    int y;
    @NonNull HAlignment hAlignment;
    @NonNull VAlignment vAlignment;

    @Builder
    public Positioning(int x, int y, HAlignment hAlignment, VAlignment vAlignment) {
        this.x = x;
        this.y = y;
        this.hAlignment = hAlignment;
        this.vAlignment = vAlignment;
    }

    public static @NonNull Positioning.PositioningBuilder builder() {
        return new PositioningBuilder().hAlignment(HAlignment.LEFT).vAlignment(VAlignment.TOP);
    }
}
