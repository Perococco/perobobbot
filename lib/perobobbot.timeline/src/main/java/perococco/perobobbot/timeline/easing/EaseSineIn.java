package perococco.perobobbot.timeline.easing;

import static perococco.perobobbot.timeline.easing.EasingConstants.HALF_PI;

public class EaseSineIn implements EasingOperator {

    @Override
    public double compute(double x) {
        return 1-Math.cos(x * HALF_PI);
    }



}
