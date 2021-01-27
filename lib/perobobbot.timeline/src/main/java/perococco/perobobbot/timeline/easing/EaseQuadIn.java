package perococco.perobobbot.timeline.easing;

public class EaseQuadIn implements EasingOperator {

    @Override
    public double compute(double x) {
        return x*x;
    }
}
