package perococco.perobobbot.timeline.easing;

import lombok.NonNull;

/**
 * All operator have been copied from <a href="https://easings.net">Easing Functions Cheat Sheet</a>
 */
public interface EasingOperator {

    double compute(double x);

    default @NonNull EasingOperator reverse() {
        return new Reversed(this);
    }

    default @NonNull EasingOperator symmetrize() {
        return new Symmetric(this);
    }
}
