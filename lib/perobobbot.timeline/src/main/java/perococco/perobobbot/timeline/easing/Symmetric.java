package perococco.perobobbot.timeline.easing;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Symmetric implements EasingOperator {

    private final @NonNull EasingOperator reference;

    @Override
    public double compute(double x) {
        if (x<0.5) {
            return 0.5* reference.compute(2*x);
        } else {
            return 0.5*(2- reference.compute(2-2*x));
        }
    }

}
