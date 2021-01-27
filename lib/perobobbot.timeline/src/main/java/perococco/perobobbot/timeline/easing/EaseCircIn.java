package perococco.perobobbot.timeline.easing;

public class EaseCircIn implements EasingOperator {

    @Override
    public double compute(double x) {
        return 1-Math.sqrt(1-x*x);
    }
}
