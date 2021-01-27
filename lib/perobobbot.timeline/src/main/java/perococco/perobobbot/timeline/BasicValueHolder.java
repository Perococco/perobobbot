package perococco.perobobbot.timeline;

import lombok.NonNull;

public class BasicValueHolder implements ValueHolder {

    private double value;

    private double time;

    public BasicValueHolder() {
    }

    public BasicValueHolder(double value, double time) {
        this.value = value;
        this.time = time;
    }

    @Override
    public BasicValueHolder set(double value) {
        this.value = value;
        return this;
    }

    @Override
    public double get() {
        return this.value;
    }

    @Override
    public BasicValueHolder setTime(double time) {
        this.time = time;
        return this;
    }

    @Override
    public BasicValueHolder clearEasing() {
        return this;
    }

    @Override
    public ValueHolderWithEasing withEasing(@NonNull Easing easing) {
        return new ValueHolderWithEasing(easing, value).setTime(time);
    }
}
