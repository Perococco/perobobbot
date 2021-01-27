package perococco.perobobbot.timeline.easing;

public class EaseQuartIn implements EasingOperator {

    @Override
    public double compute(double x) {
        var y = x*x;
        return y*y;
    }

}
