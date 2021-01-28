package perococco.perobobbot.timeline;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValueHolderWithEasing implements ValueHolder {

    private final @NonNull Easing easing;

    private double time;

    private double target;

    private double value;

    private double start;

    private double delta;

    public ValueHolderWithEasing(@NonNull Easing easing, double value) {
        this.easing = easing;
        this.value = value;
        this.start = value;
        this.delta = 0;
    }

    @Override
    public void set(double target) {
        if (Double.compare(target,this.target) == 0) {
            return;
        }
        easing.reset(time);
        start = this.value;
        delta = target-start;
        this.target = target;
    }

    @Override
    public double get() {
        return this.value;
    }

    @Override
    public void setTime(double time) {
        this.time = time;
        this.value = start + easing.getValue(time)*delta;
    }

    @Override
    public BasicValueHolder withoutEasing() {
        return new BasicValueHolder(value, time);
    }

    @Override
    public ValueHolderWithEasing withEasing(@NonNull Easing easing) {
        return new ValueHolderWithEasing(easing, start+delta);
    }
}
