package perococco.perobobbot.timeline.easing;

public class EaseQuintIn implements EasingOperator {

    @Override
    public double compute(double x) {
        var y = x*x;
        return y*y*x;
    }
}
