package perococco.perobobbot.timeline;

import lombok.NonNull;

public interface ValueHolder {

    double get();

    void setTime(double time);

    void set(double value);

    ValueHolder withoutEasing();

    ValueHolder withEasing(@NonNull Easing easing);

    double getTarget();

    void forceSet(double value);
}
