package perobobbot.overlay.api;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.function.DoubleUnaryOperator;

@RequiredArgsConstructor
public enum VAlignment {
    TOP(h -> -h),
    MIDDLE(h -> -h*0.5),
    BOTTOM(h -> 0),
    ;

    private final @NonNull DoubleUnaryOperator offsetComputer;

    public double getPosition(double height) {
        return offsetComputer.applyAsDouble(height);
    }
}
