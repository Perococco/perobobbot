package perococco.perobobbot.timeline.easing;

import perobobbot.lang.Todo;

public class EaseElasticIn implements EasingOperator {

    private static final double C4 = Math.PI*2/3;

    @Override
    public double compute(double x) {
        if (x<=0) {
            return 0;
        } else if (x>=1) {
            return 1;
        }
        var y = 10*x-10;
        return -Math.sin((y-0.75)*C4)*Math.pow(2,y);
    }


}
