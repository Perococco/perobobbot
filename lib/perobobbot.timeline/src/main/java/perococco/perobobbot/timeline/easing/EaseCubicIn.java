package perococco.perobobbot.timeline.easing;

public class EaseCubicIn implements EasingOperator {
    @Override
    public double compute(double x) {
        return x*x*x;
    }
}
