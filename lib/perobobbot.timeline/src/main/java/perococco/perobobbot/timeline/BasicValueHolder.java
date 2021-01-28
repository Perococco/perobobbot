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
    public void set(double value) {
        this.value = value;
    }

    @Override
    public double get() {
        return this.value;
    }

    @Override
    public void setTime(double time) {
        this.time = time;
    }

    @Override
    public BasicValueHolder withoutEasing() {
        return this;
    }

    @Override
    public ValueHolderWithEasing withEasing(@NonNull Easing easing) {
        final var newValue = new ValueHolderWithEasing(easing, value);
        newValue.setTime(time);
        return newValue;
    }
}
