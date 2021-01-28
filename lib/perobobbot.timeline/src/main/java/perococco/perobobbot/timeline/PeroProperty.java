package perococco.perobobbot.timeline;

import lombok.NonNull;
import perobobbot.timeline.EasingType;
import perobobbot.timeline.Property;
import perobobbot.timeline.ReadOnlyProperty;

import java.time.Duration;

public class PeroProperty implements Property, TimedItem {

    private @NonNull ValueHolder valueHolder = new BasicValueHolder();

    @Override
    public PeroProperty clearEasing() {
        this.valueHolder = valueHolder.withoutEasing();
        return this;
    }

    public void setTime(double time) {
        this.valueHolder.setTime(time);
    }

    @Override
    public PeroProperty setEasing(@NonNull EasingType easingType, @NonNull Duration easingDuration) {
        final double durationInSecond = easingDuration.toMillis()/1000.;
        if (durationInSecond<=0) {
            this.valueHolder = valueHolder.withoutEasing();
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

    @Override
    public double getTarget() {
        return valueHolder.getTarget();
    }

    @Override
    public @NonNull ReadOnlyProperty withTransformation(int factor, int offset) {
        return new TransformedProperty(this,factor,offset);
    }
}
