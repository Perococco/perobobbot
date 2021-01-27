package perococco.perobobbot.timeline;

import lombok.NonNull;
import perobobbot.timeline.EasingType;
import perobobbot.timeline.Property;

import java.time.Duration;

public class PeroProperty implements Property {

    private double value = 0;

    private double time = 0;
    private Easing easing = null;

    private double start = 0;
    private double delta = 0;
    private double target = 0;


    @Override
    public void clearEasing() {
        easing = null;
    }

    public void setTime(double time) {
        this.time = time;
        if (easing == null) {
            this.value = this.target;
        } else {
            this.value = easing.getValue(time)*delta+start;
        }
    }

    @Override
    public void setEasing(@NonNull EasingType easingType, @NonNull Duration easingDuration) {
        final double durationInSecond = easingDuration.toSeconds();
        if (durationInSecond<=0) {
            clearEasing();
        } else {
            easing = new Easing(1./durationInSecond,easingType.getEasingOperator());
        }
    }

    @Override
    public void set(double value) {
        if (easing == null) {
            this.start = value;
            this.value = value;
            this.target = value;
            this.delta = 0;
        } else {
            this.start = this.value;
            this.target = value;
            this.delta = value - this.start;
            this.easing.reset(time);
        }
    }

    @Override
    public double get() {
        return value;
    }
}
