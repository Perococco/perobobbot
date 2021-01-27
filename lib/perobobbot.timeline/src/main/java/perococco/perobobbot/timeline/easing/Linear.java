package perococco.perobobbot.timeline.easing;

import lombok.NonNull;

public class Linear implements EasingOperator {

    @Override
    public double compute(double x) {
        return x;
    }

    @Override
    public @NonNull EasingOperator reverse() {
        return this;
    }

    @Override
    public @NonNull EasingOperator symmetrize() {
        return this;
    }
}
