package perococco.perobobbot.timeline.easing;

public class EaseBackIn implements EasingOperator {

    public static final double C1 = 1.70158;
    public static final double C3 = C1+1;

    @Override
    public double compute(double x) {
        return x*x*(C3*x-C1);
    }
}
