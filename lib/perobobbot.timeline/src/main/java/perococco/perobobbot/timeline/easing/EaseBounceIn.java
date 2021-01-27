package perococco.perobobbot.timeline.easing;

public class EaseBounceIn implements EasingOperator {

    public static final double D1 = 11/4.;
    public static final double N1 = D1*D1;

    @Override
    public double compute(double x) {
        var y = D1*(1-x);
        if (y<1) {
            return 1-y*y;
        }
        if (y<2) {
            y-=1.5;
            return 0.25-y*y;
        }
        if (y<2.5) {
            y-=2.25;
            return 0.0625 - y*y;
        }
        y-=2.625;
        return 0.015625 - y*y;
    }
}
