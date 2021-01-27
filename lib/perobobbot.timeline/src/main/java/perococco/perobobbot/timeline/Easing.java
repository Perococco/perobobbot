package perococco.perobobbot.timeline;

import lombok.RequiredArgsConstructor;
import perococco.perobobbot.timeline.easing.EasingOperator;

@RequiredArgsConstructor
public class Easing {

    private final double invDuration;

    private final EasingOperator operator;

    private double startingTime;

    public void reset(double startingTime) {
        this.startingTime = startingTime;
    }

    public double getValue(double time) {
        var dt = (time-startingTime)*invDuration;
        if (dt<=0) {
            return 0;
        } if (dt>=1) {
            return 1;
        }
        return operator.compute(dt);
    }
}
