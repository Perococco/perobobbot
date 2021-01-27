package perococco.perobobbot.timeline.easing;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Reversed implements EasingOperator {

    private final @NonNull EasingOperator in;

    @Override
    public double compute(double x) {
        return 1-in.compute(1-x);
    }

    @Override
    public @NonNull EasingOperator reverse() {
        return in;
    }

}
