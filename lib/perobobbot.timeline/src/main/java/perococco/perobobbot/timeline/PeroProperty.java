package perococco.perobobbot.timeline;

import lombok.NonNull;
import perobobbot.timeline.EasingType;
import perobobbot.timeline.Property;

import java.time.Duration;

public class PeroProperty implements Property {

    private @NonNull ValueHolder valueHolder = new BasicValueHolder();

    @Override
    public PeroProperty clearEasing() {
        this.valueHolder = valueHolder.clearEasing();
        return this;
    }

    public void setTime(double time) {
        this.valueHolder.setTime(time);
    }

    @Override
    public PeroProperty setEasing(@NonNull EasingType easingType, @NonNull Duration easingDuration) {
        final double durationInSecond = easingDuration.toSeconds();
        if (durationInSecond<=0) {
            this.valueHolder = valueHolder.clearEasing();
        } else {
            this.valueHolder = this.valueHolder.withEasing(new Easing(1./durationInSecond, easingType.getEasingOperator()));
        }
        return this;
    }

    @Override
    public PeroProperty set(double value) {
        this.valueHolder.set(value);
        return this;
    }

    @Override
    public double get() {
        return valueHolder.get();
    }
}
