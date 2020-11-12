package perobobbot.overlay.api;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.function.DoubleUnaryOperator;

@RequiredArgsConstructor
public enum HAlignment {
    LEFT(w -> 0),
    MIDDLE(w -> w*0.5),
    RIGHT(w -> w),
    ;

    private final @NonNull DoubleUnaryOperator offsetComputer;

    public double getPosition(double width) {
        return offsetComputer.applyAsDouble(width);
    }

}
