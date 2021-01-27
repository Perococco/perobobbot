package perococco.perobobbot.timeline.easing;

public class EaseExpoIn implements EasingOperator {

    @Override
    public double compute(double x) {
        return x<=0?0:Math.pow(2,10*x-10);
    }
}
